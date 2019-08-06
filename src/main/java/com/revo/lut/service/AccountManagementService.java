package com.revo.lut.service;

import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.model.AccountEntity;
import com.revo.lut.util.Converter;
import io.swagger.client.model.Account;
import io.swagger.client.model.CreateAccountDetails;

import java.util.List;
import java.util.stream.Collectors;

public class AccountManagementService {

    AccountDataStore accountDataStore;

    private AccountManagementService() {
        this.accountDataStore = AccountDataStore.getInstance();
    }

    private static final AccountManagementService INSTANCE = new AccountManagementService();

    public static AccountManagementService getInstance() {
        return INSTANCE;
    }

    public Account getAccount(String accountId) {
        return Converter.convertToAccount(accountDataStore.getAccount(accountId));
    }

    public Account createAccount(CreateAccountDetails createAccountDetails) {
        AccountEntity account = Converter.convertToAccountEntity(createAccountDetails);
        account = accountDataStore.addAcount(account);
        return Converter.convertToAccount(account);
    }

    public List<Account> getAllAccounts() {
        return accountDataStore.getAllAccounts()
                .stream().map(Converter :: convertToAccount)
                .collect(Collectors.toList());
    }
}
