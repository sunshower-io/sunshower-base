package io.sunshower.persist.hibernate;


public class HibernateDialectProperties {

    private String name;


    private String dialect;

    private SearchConfiguration search;

    private DataDefinitionLanguage ddl;

    private HibernateCacheConfiguration cache;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialect() {
        return dialect;
    }


    public HibernateCacheConfiguration getCache() {
        return cache;
    }

    public void setCache(HibernateCacheConfiguration cache) {
        this.cache = cache;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public SearchConfiguration getSearch() {
        return search;
    }

    public void setSearch(SearchConfiguration search) {
        this.search = search;
    }

    public DataDefinitionLanguage getDdl() {
        return ddl;
    }

    public void setDdl(DataDefinitionLanguage ddl) {
        this.ddl = ddl;
    }
}
