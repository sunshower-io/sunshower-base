package io.sunshower.test.ws;

import io.sunshower.test.ws.cfg.TestEntity;
import io.sunshower.test.ws.cfg.TestService;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

@RunWith(JUnitPlatform.class)
@RESTTest
class RemoteExtensionTest {

    private Integer port;

    @Inject
    private TestService local;

    @Remote
    private TestService remote;


    @Test
    public void ensureServiceGetsInjected() {
        assertThat(local, is(not(nullValue())));
    }

    @Test
    public void ensurePortGetsInjected() {
        assertThat(port, is(not(nullValue())));
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