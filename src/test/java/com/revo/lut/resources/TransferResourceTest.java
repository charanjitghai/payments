package com.revo.lut.resources;

import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.model.AccountEntity;
import io.swagger.client.model.TransferCompletionDetails;
import io.swagger.client.model.TransferMoneyDetails;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.testng.AssertJUnit.assertEquals;

public class TransferResourceTest extends JerseyTest {


    @BeforeClass
    public static void init() {
        AccountDataStore accountDataStore = AccountDataStore.getInstance();
        accountDataStore.addAcount(new AccountEntity("7", new BigDecimal(1000.57)));
        accountDataStore.addAcount(new AccountEntity("8", new BigDecimal(10000.68)));
    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TransferResource.class);
    }

    @Test
    public void testTransfer() {
        final TransferCompletionDetails transferCompletionDetails =
                    target("/v1/transfers")
                    .request()
                    .post(Entity.entity(getTransferMoneyDetails(), MediaType.APPLICATION_JSON_TYPE))
                    .readEntity(TransferCompletionDetails.class);

        assertEquals("7", transferCompletionDetails.getFrom());
        assertEquals("8", transferCompletionDetails.getTo());
        assertEquals(new BigDecimal(950.47).setScale(2, RoundingMode.DOWN), transferCompletionDetails.getFromBalance().setScale(2, RoundingMode.DOWN));
        assertEquals(new BigDecimal(10050.78).setScale(2, RoundingMode.DOWN), transferCompletionDetails.getToBalance().setScale(2, RoundingMode.DOWN));
    }

    private TransferMoneyDetails getTransferMoneyDetails() {
        TransferMoneyDetails transferMoneyDetails = new TransferMoneyDetails();
        transferMoneyDetails.setFrom("7");
        transferMoneyDetails.setTo("8");
        transferMoneyDetails.setAmount(new BigDecimal(50.1));
        return transferMoneyDetails;
    }
}
