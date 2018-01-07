package io.sunshower.test.ws;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD, "application/x-javascript"})
@Consumes({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
public class JsonProvider extends MOXyJsonProvider {
}
