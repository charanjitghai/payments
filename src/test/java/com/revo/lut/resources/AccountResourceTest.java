package com.revo.lut.resources;

import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.model.AccountEntity;
import io.swagger.client.model.Account;
import io.swagger.client.model.CreateAccountDetails;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class AccountResourceTest extends JerseyTest {

    @BeforeClass
    public static void init(){
        AccountDataStore accountDataStore = AccountDataStore.getInstance();
        accountDataStore.addAcount(new AccountEntity("5", new BigDecimal(0)));
    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountResource.class);
    }

    @Test
    public void testCreateAccount() {

        final Account account = target("/v1/accounts")
                                    .request()
                                    .post(Entity.entity(getAccountDetails(), MediaType.APPLICATION_JSON_TYPE))
                                    .readEntity(Account.class);
        assertEquals(account.getBalance(), BigDecimal.ZERO);
        assertEquals(account.getId(), "6");
    }

    @Test
    public void testGetAccount() {
        final Account account = target("/v1/accounts/5")
                                .request()
                                .get()
                                .readEntity(Account.class);
        assertTrue(account.getBalance().equals(BigDecimal.ZERO));
    }

    private CreateAccountDetails getAccountDetails() {
        CreateAccountDetails accountDetails = new CreateAccountDetails();
        accountDetails.setBalance(BigDecimal.ZERO);
        accountDetails.setId("6");
        return accountDetails;
    }
}
