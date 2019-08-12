package com.revo.lut;

import com.revo.lut.resources.AccountResource;
import com.revo.lut.resources.TransferResource;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;


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
                    + GenericExceptionMapper.class.getCanonicalName() + ";"
                    + JacksonFeature.class.getCanonicalName());


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

