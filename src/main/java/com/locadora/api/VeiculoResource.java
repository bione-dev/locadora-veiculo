package com.locadora.api;

import com.locadora.model.Veiculo;
import com.locadora.service.VeiculoService;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VeiculoResource {

    @Inject
    VeiculoService veiculoService;

    @POST
    @Transactional
    public Response criarVeiculo(Veiculo veiculo) {
        Veiculo novoVeiculo = veiculoService.salvarVeiculo(veiculo);
        return Response.ok(novoVeiculo).status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response obterVeiculo(@PathParam("id") Long id) {
        Veiculo veiculo = veiculoService.buscarVeiculoPorId(id);
        if (veiculo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(veiculo).build();
    }

    @GET
    public Response listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        return Response.ok(veiculos).build();
    }
}
