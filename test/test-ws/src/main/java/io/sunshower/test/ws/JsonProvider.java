package io.sunshower.test.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

@Provider
@Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD, "application/x-javascript"})
@Consumes({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
public class JsonProvider extends MOXyJsonProvider {}
