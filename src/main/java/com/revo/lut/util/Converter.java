package com.revo.lut.util;

import com.revo.lut.model.AccountEntity;
import io.swagger.client.model.Account;
import io.swagger.client.model.CreateAccountDetails;


public class Converter {

    public static AccountEntity convertToAccountEntity(CreateAccountDetails createAccountDetails) {
        return new AccountEntity(createAccountDetails.getId(), createAccountDetails.getBalance());
    }

    /*
        Convert AccountEntity to the interface Account class object
     */
    public static Account convertToAccount(AccountEntity accountEntity) {
        Account account = new Account();
        account.setId(accountEntity.getId());
        account.setBalance(accountEntity.getBalance());
        return account;
    }
}
