package io.sunshower.persistence.auth;

import io.sunshower.common.Identifier;

import java.util.UUID;

/**
 * Created by haswell on 2/25/17.
 */
public interface Principal extends java.security.Principal {

    Identifier getId();


    int getAgentType();

}
