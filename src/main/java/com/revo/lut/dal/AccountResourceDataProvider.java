package com.revo.lut.dal;

import com.google.inject.Inject;
import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.error.IllegalAmountException;
import com.revo.lut.error.InsufficientFundsException;
import com.revo.lut.model.AccountEntity;

import java.math.BigDecimal;

public class AccountResourceDataProvider {

    private AccountDataStore accountDataStore;

    @Inject
    public AccountResourceDataProvider(AccountDataStore accountDataStore) {
        this.accountDataStore = accountDataStore;
    }

    public void credit(String id, BigDecimal amount) {
        AccountEntity account = accountDataStore.getAccount(id);
        validateAmount(amount);
        account.setBalance(account.getBalance().add(amount));
    }

    public void debit(String id, BigDecimal amount) {
        AccountEntity account = accountDataStore.getAccount(id);
        validateAmount(amount);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    String.format(
                            "Account %s has funds %s which are insufficient to complete the transaction for amount %s",
                            id, account.getBalance(), amount
                    )
            );
        }
        account.getBalance().subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new IllegalAmountException(String.format("Transaction with amount %s not allowed", amount));
        }
    }

}
