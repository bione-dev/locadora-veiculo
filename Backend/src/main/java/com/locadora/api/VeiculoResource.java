package com.locadora.api;

import com.locadora.model.Veiculo;
import com.locadora.service.VeiculoService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.net.URI;
import java.util.List;

@Path("/veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VeiculoResource {

    @Inject
    VeiculoService veiculoService;

    /**
     * Criar um novo veículo.
     */
    @POST
    @Transactional
    @Operation(summary = "Criar Veículo", description = "Cria um novo veículo com base nos dados fornecidos.")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Veículo criado com sucesso", content = @Content(schema = @Schema(implementation = Veiculo.class))),
            @APIResponse(responseCode = "400", description = "Dados inválidos ou incompletos")
    })
    public Response criarVeiculo(@Valid @NotNull Veiculo veiculo) {
        try {
            Veiculo novoVeiculo = veiculoService.salvarVeiculo(veiculo);
            URI location = UriBuilder.fromPath("/veiculos/{id}")
                    .build(novoVeiculo.getId());
            return Response.created(location).entity(novoVeiculo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar o veículo: " + e.getMessage()).build();
        }
    }

    /**
     * Obter um veículo pelo ID.
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Obter Veículo por ID", description = "Busca um veículo pelo ID fornecido.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Veículo encontrado", content = @Content(schema = @Schema(implementation = Veiculo.class))),
            @APIResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Response obterVeiculo(@PathParam("id") Long id) {
        try {
            Veiculo veiculo = veiculoService.buscarVeiculoPorId(id);
            if (veiculo == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Veículo não encontrado com o ID: " + id).build();
            }
            return Response.ok(veiculo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar o veículo: " + e.getMessage()).build();
        }
    }

    /**
     * Listar veículos com paginação e ordenação.
     */
    @GET
    @Operation(summary = "Listar Veículos", description = "Lista todos os veículos com paginação e ordenação.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Lista de veículos retornada", content = @Content(schema = @Schema(implementation = Veiculo.class))),
            @APIResponse(responseCode = "204", description = "Nenhum veículo encontrado")
    })
    public Response listarVeiculos(
            @Parameter(description = "Número da página para paginação") @QueryParam("pagina") @DefaultValue("0") int pagina,
            @Parameter(description = "Quantidade de itens por página") @QueryParam("tamanho") @DefaultValue("10") int tamanho,
            @Parameter(description = "Campo de ordenação") @QueryParam("ordenarPor") @DefaultValue("id") String ordenarPor,
            @Parameter(description = "Direção da ordenação (asc/desc)") @QueryParam("direcao") @DefaultValue("asc") String direcao) {
        try {
            List<Veiculo> veiculos = veiculoService.listarTodosPaginados(pagina, tamanho, ordenarPor, direcao);
            if (veiculos.isEmpty()) {
                return Response.noContent().build(); // HTTP 204 se não houver veículos
            }
            return Response.ok(veiculos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar os veículos: " + e.getMessage()).build();
        }
    }

    /**
     * Listar veículos por categoria.
     */
    @GET
    @Path("/categoria/{categoriaNome}")
    @Operation(summary = "Listar Veículos por Categoria", description = "Lista todos os veículos associados a uma categoria específica.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Lista de veículos retornada", content = @Content(schema = @Schema(implementation = Veiculo.class))),
            @APIResponse(responseCode = "204", description = "Nenhum veículo encontrado para a categoria fornecida")
    })
    public Response listarVeiculosPorCategoria(@PathParam("categoriaNome") String categoriaNome) {
        try {
            List<Veiculo> veiculos = veiculoService.listarVeiculosPorCategoria(categoriaNome);
            if (veiculos.isEmpty()) {
                return Response.noContent().build(); // HTTP 204 se não houver veículos
            }
            return Response.ok(veiculos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar os veículos por categoria: " + e.getMessage()).build();
        }
    }
}
