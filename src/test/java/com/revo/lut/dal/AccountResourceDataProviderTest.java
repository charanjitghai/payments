package com.revo.lut.dal;

import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.error.IllegalAmountException;
import com.revo.lut.error.InsufficientFundsException;
import com.revo.lut.model.AccountEntity;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountResourceDataProviderTest {


    static AccountResourceDataProvider accountResourceDataProvider;
    static AccountDataStore accountDataStore;

    @BeforeClass
    public static void setup() {
        accountDataStore = AccountDataStore.getInstance();
        accountDataStore.addAcount(new AccountEntity("1", new BigDecimal(100)));
        accountDataStore.addAcount(new AccountEntity("2", new BigDecimal(100)));
        accountDataStore.addAcount(new AccountEntity("3", new BigDecimal(100)));
        accountDataStore.addAcount(new AccountEntity("4", new BigDecimal(100)));
        accountResourceDataProvider = AccountResourceDataProvider.getInstance();
    }

    @Test
    public void testCredit() {
        accountResourceDataProvider.credit("1", new BigDecimal(100));
        Assert.assertEquals(accountDataStore.getAccount("1").getBalance(), new BigDecimal(200));
    }

    @Test(expected = IllegalAmountException.class)
    public void testNegativeCredit() {
        accountResourceDataProvider.credit("2", new BigDecimal(-5));
    }


    @Test
    public void testDebit() {
        accountResourceDataProvider.debit("3", new BigDecimal(100));
        Assert.assertEquals(BigDecimal.ZERO, accountDataStore.getAccount("3").getBalance());
    }

    @Test(expected = InsufficientFundsException.class)
    public void testOverdraft() {
        accountResourceDataProvider.debit("4", new BigDecimal(1000));
    }
}
