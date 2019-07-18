package com.revo.lut.resources;

import com.revo.lut.dal.AccountResourceDataProvider;
import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.error.IncompleteTransferDetailsException;
import com.revo.lut.error.SelfTransferException;
import com.revo.lut.model.AccountEntity;
import io.swagger.client.model.TransferCompletionDetails;
import io.swagger.client.model.TransferMoneyDetails;

import javax.annotation.Nonnull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces({"application/json"})
public class TransferResource {

    private AccountDataStore accountDataStore;
    private AccountResourceDataProvider accountResourceDataProvider;

    public TransferResource() {
        this.accountDataStore = AccountDataStore.getInstance();
        this.accountResourceDataProvider = AccountResourceDataProvider.getInstance();
    }

    @POST
    @Path("transfers")
    public Response transfer(@Nonnull TransferMoneyDetails transferMoneyDetails) {
        validate(transferMoneyDetails);
        AccountEntity fromAccount = accountDataStore.getAccount(transferMoneyDetails.getFrom());
        AccountEntity toAccount = accountDataStore.getAccount(transferMoneyDetails.getTo());
        TransferCompletionDetails transferCompletionDetails = new TransferCompletionDetails();
        transferCompletionDetails.setFrom(fromAccount.getId());
        transferCompletionDetails.setTo(toAccount.getId());
        if (transferMoneyDetails.getFrom().compareTo(transferMoneyDetails.getTo()) > 0) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    accountResourceDataProvider.debit(fromAccount.getId(), transferMoneyDetails.getAmount());
                    accountResourceDataProvider.credit(toAccount.getId(), transferMoneyDetails.getAmount());
                    transferCompletionDetails.setFromBalance(fromAccount.getBalance());
                    transferCompletionDetails.setToBalance(toAccount.getBalance());
                    return Response.status(Response.Status.OK).entity(transferCompletionDetails).build();
                }
            }
        } else {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    accountResourceDataProvider.debit(fromAccount.getId(), transferMoneyDetails.getAmount());
                    accountResourceDataProvider.credit(toAccount.getId(), transferMoneyDetails.getAmount());
                    transferCompletionDetails.setFromBalance(fromAccount.getBalance());
                    transferCompletionDetails.setToBalance(toAccount.getBalance());
                    return Response.status(Response.Status.OK).entity(transferCompletionDetails).build();
                }
            }
        }

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
