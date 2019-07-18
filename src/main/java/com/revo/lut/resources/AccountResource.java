package com.revo.lut.resources;

import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.model.AccountEntity;
import com.revo.lut.util.Converter;
import io.swagger.client.model.CreateAccountDetails;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/v1/accounts")
@Produces({"application/json"})
@Slf4j
public class AccountResource {

    AccountDataStore accountDataStore;

    public AccountResource() {
        this.accountDataStore = AccountDataStore.getInstance();
    }

    @GET
    @Path("/{id}")
    public Response getAccount(@Nonnull @PathParam("id") String accountId) {
        log.info("accountId {}", accountId);
        return Response.status(Response.Status.OK).entity(Converter.convertToAccount(accountDataStore.getAccount(accountId))).build();
    }

    @POST
    @Path("/")
    public Response createAccount(@Nonnull CreateAccountDetails createAccountDetails) {
        log.info("accountId {} {}", createAccountDetails.getId(), createAccountDetails.getBalance());
        AccountEntity account = Converter.convertToAccountEntity(createAccountDetails);
        account = accountDataStore.addAcount(account);
        return Response.status(Response.Status.OK).entity(Converter.convertToAccount(account)).build();
    }

    @GET
    @Path("/")
    public Response getAllAccounts() {
        return Response.status(Response.Status.OK)
                .entity(
                        accountDataStore.getAllAccounts()
                                .stream().map(Converter :: convertToAccount)
                                .collect(Collectors.toList())
                ).build();
    }
}
