package com.test;

import java.awt.*;
import java.io.IOException;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;

import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.StaticHttpHandler;

/**
 * The sample demonstrates how to make Jersey-Spring
 * integration work on top of Grizzly 2, including static pages served from
 * a folder or from within a jar file.
 */
public class Start {

    public static void main(String[] args) throws IOException {

        // Initialize Grizzly HttpServer
        HttpServer server = new HttpServer();
        NetworkListener listener = new NetworkListener("grizzly2", "localhost", 3388);
        server.addListener(listener);
        
        // Initialize and add Spring-aware Jersey resource
        WebappContext ctx = new WebappContext("ctx", "/api");
        final ServletRegistration reg = ctx.addServlet("spring", new SpringServlet());
        reg.addMapping("/*");
        ctx.addContextInitParameter("contextConfigLocation", "classpath:spring-context.xml");
        ctx.addListener("org.springframework.web.context.ContextLoaderListener");
        ctx.addListener("org.springframework.web.context.request.RequestContextListener");
        ctx.deploy(server);
        
        // Add the StaticHttpHandler to serve static resources from the static1 folder
        server.getServerConfiguration().addHttpHandler(
                new StaticHttpHandler("src/main/resources/webapp/static1/"), "/static");

        // Add the CLStaticHttpHandler to serve static resources located at
        // the static2 folder from the jar file jersey1-grizzly2-spring-1.0-SNAPSHOT.jar
        server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Start.class.getClassLoader(), "webapp/static2/"), "/jarstatic");

        try {
            server.start();

            if(Desktop.isDesktopSupported()){
              try {
                //Desktop.getDesktop().browse(new URI("http://localhost:3388/api"));
                new RichClient("http://localhost:3388/jarstatic/angularjs/index.html");
              }
              catch (Exception e) {
                e.printStackTrace();
              }
            }

            System.out.println(new File("target/jersey1-grizzly2-spring-1.0-SNAPSHOT.jar").toURI().toURL());
            System.out.println("http://localhost:3388/api to see the default TestResource.getIt() resource");
            System.out.println("http://localhost:3388/api/test to see the TestResource.test() resource");
            System.out.println("http://localhost:3388/api/test2 to see the TestResource.test2() resource");
            System.out.println("http://localhost:3388/static/ to see the index.html from the webapp/static1 folder");
            System.out.println("http://localhost:3388/jarstatic/ to see the index.html from the webapp/static2 folder served from the jar file");
            
            //System.out.println();
            //System.out.println("Press enter to stop the server...");
            //System.in.read();
        } finally {
            //server.shutdownNow();
        }

        
    }
}
