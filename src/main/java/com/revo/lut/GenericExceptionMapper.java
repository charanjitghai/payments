package com.revo.lut;

import com.revo.lut.error.AccountAlreadyExistsException;
import com.revo.lut.error.AccountDoesNotExistException;
import com.revo.lut.error.IllegalAmountException;
import com.revo.lut.error.IncompleteTransferDetailsException;
import com.revo.lut.error.InsufficientFundsException;
import com.revo.lut.error.SelfTransferException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof AccountAlreadyExistsException) {
            return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
        }
        if (ex instanceof AccountDoesNotExistException) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
        if (ex instanceof IllegalAmountException || ex instanceof IncompleteTransferDetailsException || ex instanceof SelfTransferException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        if (ex instanceof InsufficientFundsException) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(ex.getMessage()).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
    }
}