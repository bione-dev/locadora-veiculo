package com.locadora.api;

import com.locadora.model.Categoria;
import com.locadora.service.CategoriaService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @POST
    @Transactional
    public Response criarCategoria(@Valid @NotNull Categoria categoria) {
        try {
            Categoria novaCategoria = categoriaService.adicionarCategoria(categoria);
            return Response.status(Response.Status.CREATED).entity(novaCategoria).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar categoria: " + e.getMessage()).build();
        }
    }

    @GET
    public Response listarCategorias() {
        try {
            return Response.ok(categoriaService.listarCategorias()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar categorias: " + e.getMessage()).build();
        }
    }
}
