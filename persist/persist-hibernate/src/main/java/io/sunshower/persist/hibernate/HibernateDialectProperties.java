package io.sunshower.persist.hibernate;


public interface HibernateDialectProperties {

     String name();

     String dialect();

     SearchConfiguration search();

     DataDefinitionLanguage ddl();
     
     HibernateCacheConfiguration cache();
}
