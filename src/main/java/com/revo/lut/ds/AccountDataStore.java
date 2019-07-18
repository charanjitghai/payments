package com.revo.lut.ds;

import com.revo.lut.error.AccountAlreadyExistsException;
import com.revo.lut.error.AccountDoesNotExistException;
import com.revo.lut.model.AccountEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDataStore {

    private static final AccountDataStore INSTANCE = new AccountDataStore();

    public static AccountDataStore getInstance() {
        return INSTANCE;
    }

    Map<String, AccountEntity> accounts;

    private AccountDataStore() {
        this.accounts = new ConcurrentHashMap<String, AccountEntity>();
    }

    public AccountEntity addAcount(AccountEntity account) {
        AccountEntity existingAccount = accounts.putIfAbsent(account.getId(), account);
        if (existingAccount != null) {
            throw new AccountAlreadyExistsException(String.format("Account with id %s already exists", account.getId()));
        }
        return accounts.get(account.getId());
    }

    public AccountEntity getAccount(String id) {
        AccountEntity account = accounts.get(id);
        if (account == null) {
            throw new AccountDoesNotExistException(String.format("Account with id %s doesn't exist", id));
        }
        return account;
    }

    public Collection<AccountEntity> getAllAccounts() {
        return accounts.values();
    }
}
