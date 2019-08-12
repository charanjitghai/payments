package com.revo.lut.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.lut.GenericExceptionMapper;
import com.revo.lut.ds.AccountDataStore;
import com.revo.lut.model.AccountEntity;
import io.swagger.client.model.TransferMoneyDetails;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.testng.AssertJUnit.assertEquals;

public class TransferResourceTest extends JerseyTest {


    @BeforeClass
    public static void init() {
        AccountDataStore accountDataStore = AccountDataStore.getInstance();
        accountDataStore.addAcount(new AccountEntity("7", new BigDecimal(1000.57)));
        accountDataStore.addAcount(new AccountEntity("8", new BigDecimal(10000.68)));
        accountDataStore.addAcount(new AccountEntity("9", new BigDecimal(10.68)));
        accountDataStore.addAcount(new AccountEntity("10", new BigDecimal(11.68)));
    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TransferResource.class, GenericExceptionMapper.class);
    }

    @Test
    public void testTransfer() throws IOException {
        Response response = target("/v1/transfers")
                .request()
                .post(Entity.entity(getTransferMoneyDetails(), MediaType.APPLICATION_JSON_TYPE));
        HashMap<String,Object> result =
                new ObjectMapper().readValue(response.readEntity(String.class), HashMap.class);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("7", result.get("from"));
        assertEquals("8", result.get("to"));
        assertEquals("950.47", result.get("fromBalance").toString());
        assertEquals("10050.78", result.get("toBalance").toString());
    }


    @Test
    public void testOverDraft() throws IOException {
        Response response = target("/v1/transfers")
                .request()
                .post(Entity.entity(getOverdraftTransferMoneyDetails(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), response.getStatus());
    }

    private TransferMoneyDetails getTransferMoneyDetails() {
        TransferMoneyDetails transferMoneyDetails = new TransferMoneyDetails();
        transferMoneyDetails.setFrom("7");
        transferMoneyDetails.setTo("8");
        transferMoneyDetails.setAmount(new BigDecimal(50.1));
        return transferMoneyDetails;
    }

    private TransferMoneyDetails getOverdraftTransferMoneyDetails() {
        TransferMoneyDetails transferMoneyDetails = new TransferMoneyDetails();
        transferMoneyDetails.setFrom("9");
        transferMoneyDetails.setTo("10");
        transferMoneyDetails.setAmount(new BigDecimal(11));
        return transferMoneyDetails;
    }

}
