package com.soap.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RowNotExistsExceptionMapper implements ExceptionMapper<RowNotExistsException> {
    @Override
    public Response toResponse(RowNotExistsException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}