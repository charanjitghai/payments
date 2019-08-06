package com.revo.lut.resources;

import com.revo.lut.error.IncompleteTransferDetailsException;
import com.revo.lut.error.SelfTransferException;
import com.revo.lut.service.TransferManagementService;
import io.swagger.client.model.TransferMoneyDetails;

import javax.annotation.Nonnull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces({"application/json"})
public class TransferResource {

    private TransferManagementService transferManagementService;

    public TransferResource() {
        this.transferManagementService = TransferManagementService.getInstance();
    }

    @POST
    @Path("transfers")
    public Response transfer(@Nonnull TransferMoneyDetails transferMoneyDetails) {
        validate(transferMoneyDetails);
        return Response.status(Response.Status.OK).entity(transferManagementService.transfer(transferMoneyDetails)).build();
    }

    private void validate(TransferMoneyDetails transferMoneyDetails) {
        if (transferMoneyDetails.getFrom() == null) {
            throw new IncompleteTransferDetailsException("Missing required attribute 'from account id'");
        }
        if (transferMoneyDetails.getTo() == null) {
            throw new IncompleteTransferDetailsException("Missing required attribute 'to account id'");
        }
        if (transferMoneyDetails.getAmount() == null) {
            throw new IncompleteTransferDetailsException("Missing required attribute 'transfer amount'");
        }
        if (transferMoneyDetails.getTo().equals(transferMoneyDetails.getFrom())) {
            throw new SelfTransferException("From accountId and To accountId can't be same");
        }
    }
}
