package com.soap.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MarkFieldExceptionMapper implements ExceptionMapper<MarkFieldException> {
    @Override
    public Response toResponse(MarkFieldException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}
