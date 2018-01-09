package io.sunshower.test.ws;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class SunshowerTestServer {

  static final Logger log = Logger.getLogger(SunshowerTestServer.class.getName());

  final int port;
  final UndertowJaxrsServer server;
  final String location;
  private final ResteasyDeployment deployment;
  private final EnableJAXRS configuration;

  public SunshowerTestServer(EnableJAXRS configuration, ResteasyDeployment deployment) {
    checkDeployment(deployment);
    checkConfiguration(configuration);
    this.deployment = deployment;
    this.configuration = configuration;
    this.location = resolveLocation();
    this.port = resolvePort();
    this.server = createServer();
  }

  public String getLocation() {
    return String.format("http://%s:%s/", location, port);
  }

  @PostConstruct
  public void start() {
    log.log(Level.INFO, "Starting sunshower test server...");
    DeploymentInfo deployment = server.undertowDeployment(this.deployment);
    deployment.setContextPath("/");
    deployment.setClassLoader(getClass().getClassLoader());
    deployment.setDeploymentName("sunshower-test" + hashCode());
    server.deploy(deployment);
    log.log(Level.INFO, "Sunshower test server started");
  }

  @PreDestroy
  public void stop() {
    log.log(Level.INFO, "Stopping sunshower test server...");
    server.stop();
    log.log(Level.INFO, "Sunshower test server stopped.");
  }

  private String resolveLocation() {
    return configuration.location();
  }

  private int resolvePort() {
    int port = configuration.port();
    if (port == -1) {
      return randomPort();
    }
    log.log(Level.INFO, "Attempting to start server on requested port {0}", port);
    return port;
  }

  private UndertowJaxrsServer createServer() {
    Undertow.Builder builder =
        Undertow.builder().addHttpListener(port, location).setWorkerThreads(2).setIoThreads(2);
    return new UndertowJaxrsServer().start(builder);
  }

  private int randomPort() {
    log.log(Level.INFO, "Starting on a random port...");
    int timeout = 200;
    for (int i = 65535; i > 1000; i--) {
      try {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(location, i), timeout);
        socket.close();
      } catch (Exception ex) {
        log.log(Level.INFO, "Found local port: {0}", i);
        return i;
      }
    }
    throw new IllegalStateException("Couldn't find any open ports.  Can't continue");
  }

  private void checkConfiguration(EnableJAXRS configuration) {
    if (configuration == null) {
      log.log(
          Level.SEVERE,
          "Your test class was not annotated with @EnableJAXRS.  "
              + "I don't know how you got here, but this isn't the right place for you.");
      throw new IllegalStateException("Error: @EnableJAXRS was not present.  Can't continue");
    }
    log.log(Level.INFO, "Found @EnableJAXRS with value {0}", configuration);
  }

  private void checkDeployment(ResteasyDeployment deployment) {
    if (deployment == null) {
      log.log(Level.SEVERE, "No deployment found. Can't continue (this is probably a bug)");
      throw new IllegalStateException("Error: @EnableJAXRS was not present.  Can't continue");
    }
    log.log(Level.INFO, "Found @EnableJAXRS with value {0}", deployment);
  }
}
