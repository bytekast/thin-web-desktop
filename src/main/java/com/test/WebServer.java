package com.test;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SocketUtils;

import java.io.IOException;


public class WebServer {

  private static final Logger log = LoggerFactory.getLogger(WebServer.class);

  private HttpServer server = new HttpServer();

  private int port;

  public int getPort() {
    return port;
  }

  public void start() throws IOException {

    NetworkListener listener = new NetworkListener("grizzly2", "localhost", SocketUtils.findAvailableTcpPort());
    port = listener.getPort();
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
    server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(WebServer.class.getClassLoader(), "webapp/static2/"), "/jarstatic");

    try {
      log.info("Starting server on port: " + getPort());
      server.start();
    }
    finally {

    }
  }

  public void stop(){
    log.info("Shutting down server on port: " + getPort());
    server.shutdownNow();
  }
}
