package io.sunshower.test.ws.cfg;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("test")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface TestService {

  @POST
  @Path("test")
  TestEntity save(TestEntity testEntity);

  @GET
  @Path("{value}")
  @Produces({MediaType.TEXT_PLAIN})
  String call(@PathParam("value") String input);
}
