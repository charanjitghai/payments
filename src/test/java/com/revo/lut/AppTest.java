package com.revo.lut;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revo.lut.dal.AccountResourceDataProvider;
import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.error.AccountAlreadyExistsException;
import com.revo.lut.error.AccountDoesNotExistException;
import com.revo.lut.error.IllegalAmountException;
import com.revo.lut.error.InsufficientFundsException;
import com.revo.lut.model.AccountEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Unit test for simple App.
 */

public class AppTest
{
    static AccountDataStore accountDataStore;
    static AccountResourceDataProvider accountResourceDataProvider;
    @BeforeAll
    public static void setup() {
        Injector injector = Guice.createInjector(new PaymentsModule());
        accountDataStore = injector.getInstance(AccountDataStore.class);
        accountResourceDataProvider = injector.getInstance(AccountResourceDataProvider.class);
    }

    @org.junit.jupiter.api.Test
    public void testAccountCreation() {
//        Injector injector = Guice.createInjector(new PaymentsModule());
//        accountDataStore = injector.getInstance(AccountDataStore.class);
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        Assert.assertEquals(accountDataStore.getAccount(id).getBalance(), new BigDecimal(100));
    }

    @org.junit.jupiter.api.Test
    public void testDuplicateAccount() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(10))));
    }

    @org.junit.jupiter.api.Test
    public void testNonExistingAccountGet() {
        Assertions.assertThrows(AccountDoesNotExistException.class, () -> accountDataStore.getAccount(UUID.randomUUID().toString()));
    }


    @org.junit.jupiter.api.Test
    public void testCredit() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        accountResourceDataProvider.credit(id, new BigDecimal(100));
        Assert.assertEquals(accountDataStore.getAccount(id).getBalance(), new BigDecimal(200));
    }

    @org.junit.jupiter.api.Test
    public void testNegativeCredit() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        Assertions.assertThrows(IllegalAmountException.class, () -> accountResourceDataProvider.credit(id, new BigDecimal(-5)));
    }


    @org.junit.jupiter.api.Test
    public void testDebit() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        accountResourceDataProvider.debit(id, new BigDecimal(100));
        Assert.assertEquals(accountDataStore.getAccount(id).getBalance(), BigDecimal.ZERO);
    }

    @org.junit.jupiter.api.Test
    public void testOverdraft() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        Assertions.assertThrows(InsufficientFundsException.class, () -> accountResourceDataProvider.debit(id, new BigDecimal(1000)));
    }
}
