package io.sunshower.persist.hibernate;

public interface HibernateCacheConfiguration {

    String fabricName();

    boolean enabled();


    String provider();

    boolean queryCacheEnabled();

    boolean generateStatistics();

    String regionFactory();


    String releaseMode();


    String accessType();

}
