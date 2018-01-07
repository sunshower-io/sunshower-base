package io.sunshower.test.ws;

import io.sunshower.test.ws.cfg.TestConfiguration;
import io.sunshower.test.ws.cfg.TestEntity;
import io.sunshower.test.ws.cfg.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

@EnableJAXRS
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestConfiguration.class
})
class RemoteExtensionTest{
    

    @Inject
    private Client client;

    @Inject
    private TestService local;

    @Remote
    private TestService remote;


    @Inject
    private WebTarget webTarget;
    
    @Test
    public void ensureClientIsInjected() {
        assertThat(client, is(not(nullValue())));
    }

    @Test
    public void ensureWebTargetIsInjected() {
        assertThat(webTarget, is(not(nullValue())));
    }
    
    @Test
    public void ensureRemoteServiceIsInjected() {
        assertThat(remote, is(not(nullValue())));
    }

    @Test
    public void ensureServiceGetsInjected() {
        assertThat(local, is(not(nullValue())));
    }


    @Test
    public void ensureRemoteIsInjected() {
        assertThat(remote, is(not(nullValue())));
    }

    @Test
    public void ensureRemoteTargetIsCallableWithJaxRSEntity() {
        assertThat(remote.save(new TestEntity("hello")).getName(), is("hello"));
    }


    @Test
    public void ensureRemoteTargetIsCallable() {
        assertThat(remote.call("frapper"), is("Called with: frapper"));
    }

}