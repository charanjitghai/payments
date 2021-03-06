package com.revo.lut.resources;

import com.revo.lut.service.AccountManagementService;
import com.revo.lut.util.Validator;
import io.swagger.client.model.CreateAccountDetails;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/accounts")
@Slf4j
public class AccountResource {

    AccountManagementService accountManagementService;

    public AccountResource() {
        this.accountManagementService = AccountManagementService.getInstance();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@Nonnull @PathParam("id") String accountId) {
        log.info("accountId {}", accountId);
        Validator.validateAccountId(accountId);
        return Response.status(Response.Status.OK).entity(accountManagementService.getAccount(accountId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@Nonnull CreateAccountDetails createAccountDetails) {
        log.info("accountId {} accountBalance {}", createAccountDetails.getId(), createAccountDetails.getBalance());
        Validator.validateAccountId(createAccountDetails.getId());
        Validator.validateAmount(createAccountDetails.getBalance());
        return Response.status(Response.Status.OK).entity(accountManagementService.createAccount(createAccountDetails)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        log.info("getting all accounts");
        return Response.status(Response.Status.OK)
                .entity(
                        accountManagementService.getAllAccounts()
                ).build();
    }

}
