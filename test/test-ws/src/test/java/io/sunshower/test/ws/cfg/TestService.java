package io.sunshower.test.ws.cfg;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Path("test")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface TestService {

  @GET
  @Path("{id}/events")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  void subscribe(@PathParam("id") String id, @Context SseEventSink sink, @Context Sse sse);

  @POST
  @Path("test")
  TestEntity save(TestEntity testEntity);

  @GET
  @Path("{value}")
  @Produces({MediaType.TEXT_PLAIN})
  String call(@PathParam("value") String input);
}
