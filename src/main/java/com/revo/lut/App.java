package com.revo.lut;

import com.revo.lut.error.AccountAlreadyExistsException;
import com.revo.lut.error.AccountDoesNotExistException;
import com.revo.lut.error.IllegalAmountException;
import com.revo.lut.error.IncompleteTransferDetailsException;
import com.revo.lut.error.InsufficientFundsException;
import com.revo.lut.error.SelfTransferException;
import com.revo.lut.resources.AccountResource;
import com.revo.lut.resources.TransferResource;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


@Slf4j
public class App 
{
    public void run() throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);


        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                TransferResource.class.getCanonicalName() + ";"
                    +  AccountResource.class.getCanonicalName() + ";"
                    + GenericExceptionMapper.class.getCanonicalName());


        try {
            jettyServer.start();
            jettyServer.join();
        }  catch (Exception e){
            log.error("Exception", e);
            jettyServer.stop();
            jettyServer.destroy();
        }
    }

    public static void main( String[] args ) throws Exception {
        new App().run();
    }
}

class GenericExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof AccountAlreadyExistsException) {
            return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
        }
        if (ex instanceof AccountDoesNotExistException) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
        if (ex instanceof IllegalAmountException || ex instanceof IncompleteTransferDetailsException || ex instanceof SelfTransferException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        if (ex instanceof InsufficientFundsException) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(ex.getMessage()).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
    }
}
