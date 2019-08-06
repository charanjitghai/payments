package com.revo.lut.service;

import com.revo.lut.dal.AccountResourceDataProvider;
import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.model.AccountEntity;
import io.swagger.client.model.TransferCompletionDetails;
import io.swagger.client.model.TransferMoneyDetails;

public class TransferManagementService {
    private static final TransferManagementService INSTANCE = new TransferManagementService();
    private AccountDataStore accountDataStore;
    private AccountResourceDataProvider accountResourceDataProvider;

    private TransferManagementService() {
        this.accountDataStore = AccountDataStore.getInstance();
        this.accountResourceDataProvider = AccountResourceDataProvider.getInstance();
    }

    public static TransferManagementService getInstance() {
        return INSTANCE;
    }


    public TransferCompletionDetails transfer(TransferMoneyDetails transferMoneyDetails) {

        AccountEntity fromAccount = accountDataStore.getAccount(transferMoneyDetails.getFrom());
        AccountEntity toAccount = accountDataStore.getAccount(transferMoneyDetails.getTo());

        /* construct the transferCompletionDetails outside
            of the synchronized blocks for minimalistic locking.
         */
        TransferCompletionDetails transferCompletionDetails = new TransferCompletionDetails();
        transferCompletionDetails.setFrom(fromAccount.getId());
        transferCompletionDetails.setTo(toAccount.getId());

        /*
            compare the accountIds of fromAccount and toAccount to determine the order
            of locking. This ensures that there's an order in which the objects are locked
            so that the "Cyclic Wait" condition never occurs. The account with lower id has
            higher priority and would need to be locked first. The "from" account and "to" account
            can't have same id as in this case the validate method throws SelfTransferException.
         */
        if (transferMoneyDetails.getFrom().compareTo(transferMoneyDetails.getTo()) > 0) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    accountResourceDataProvider.debit(fromAccount.getId(), transferMoneyDetails.getAmount());
                    accountResourceDataProvider.credit(toAccount.getId(), transferMoneyDetails.getAmount());
                    transferCompletionDetails.setFromBalance(fromAccount.getBalance());
                    transferCompletionDetails.setToBalance(toAccount.getBalance());
                }
            }
        } else {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    accountResourceDataProvider.debit(fromAccount.getId(), transferMoneyDetails.getAmount());
                    accountResourceDataProvider.credit(toAccount.getId(), transferMoneyDetails.getAmount());
                    transferCompletionDetails.setFromBalance(fromAccount.getBalance());
                    transferCompletionDetails.setToBalance(toAccount.getBalance());
                }
            }
        }

        return transferCompletionDetails;
    }

}
