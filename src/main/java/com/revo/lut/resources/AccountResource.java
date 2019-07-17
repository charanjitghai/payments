package com.revo.lut.resources;

import io.swagger.client.model.Account;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/v1/accounts")
@Produces({"application/json"})
public class AccountResource {
    @GET
    @Path("/{id}")
    public Response getAccount(@Nonnull @PathParam("id") String accountId) {
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(new BigDecimal(0));
        return Response.status(Response.Status.OK).entity(account).build();
    }
}
