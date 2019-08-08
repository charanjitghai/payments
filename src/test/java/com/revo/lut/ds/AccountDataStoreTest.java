package com.revo.lut.ds;

import com.revo.lut.error.AccountAlreadyExistsException;
import com.revo.lut.error.AccountDoesNotExistException;
import com.revo.lut.model.AccountEntity;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDataStoreTest {
    static AccountDataStore accountDataStore;

    @BeforeClass
    public static void setup() {
        accountDataStore = AccountDataStore.getInstance();
    }

    @Test
    public void testAccountCreation() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        Assert.assertEquals(accountDataStore.getAccount(id).getBalance(), new BigDecimal(100));
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void testDuplicateAccount() {
        String id = UUID.randomUUID().toString();
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(100)));
        accountDataStore.addAcount(new AccountEntity(id, new BigDecimal(10)));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testNonExistingAccountGet() {
        accountDataStore.getAccount(UUID.randomUUID().toString());
    }
}
