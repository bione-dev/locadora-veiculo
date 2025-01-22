package com.locadora.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Estruturação das mensagens de erro como uma lista de objetos JSON
        List<Map<String, String>> errors = exception.getConstraintViolations().stream()
                .map(violation -> Map.of(
                        "field", violation.getPropertyPath().toString(), // Campo inválido
                        "message", violation.getMessage()                // Mensagem de erro
                ))
                .collect(Collectors.toList());

        // Retorno da resposta com status 400 (Bad Request) e os erros estruturados
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("errors", errors))
                .build();
    }
}
