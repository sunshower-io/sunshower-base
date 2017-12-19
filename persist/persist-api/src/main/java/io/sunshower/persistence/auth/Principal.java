package io.sunshower.persistence.auth;

import io.sunshower.common.Identifier;

import java.util.UUID;


public interface Principal extends java.security.Principal {

    Identifier getId();


    int getAgentType();

}
