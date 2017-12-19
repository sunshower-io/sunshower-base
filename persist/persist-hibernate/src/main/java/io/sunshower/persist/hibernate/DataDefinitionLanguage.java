package io.sunshower.persist.hibernate;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by haswell on 5/2/17.
 */
public class DataDefinitionLanguage {
    private String strategy;

    private boolean generate;


    @Value("${jpa.provider.ddl.generate.show-sql}")
    private boolean showSql;


    @Value("${jpa.provider.ddl.generate.format-sql}")
    private boolean formatSql;




    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}
