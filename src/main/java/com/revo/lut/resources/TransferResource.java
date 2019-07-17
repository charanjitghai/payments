package com.revo.lut.resources;

import io.swagger.client.model.Account;
import io.swagger.client.model.TransferCompletionDetails;
import io.swagger.client.model.TransferMoneyDetails;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces({"application/json"})
public class TransferResource {
    @POST
    @Path("transfers")
    public Response transfer(@Nonnull TransferMoneyDetails transferMoneyDetails) {
        TransferCompletionDetails transferCompletionDetails = new TransferCompletionDetails();
        transferCompletionDetails.setFrom(transferMoneyDetails.getFrom());
        transferCompletionDetails.setTo(transferMoneyDetails.getTo());
        transferCompletionDetails.setFromBalance(transferMoneyDetails.getAmount());
        transferCompletionDetails.setToBalance(transferMoneyDetails.getAmount());
        return Response.status(Response.Status.OK).entity(transferCompletionDetails).build();
    }
    @GET
    @Path("transfers")
    public Response transferGet() {
        return Response.status(Response.Status.OK).entity(new Account()).build();
    }
}
