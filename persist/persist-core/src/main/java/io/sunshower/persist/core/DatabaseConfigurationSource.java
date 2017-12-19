package io.sunshower.persist.core;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("jdbc")
public class DatabaseConfigurationSource {


    private String url;

    private String username;

    private String password;

    @Value("${jdbc.driver-class}")
    private String driverClass;

    @Value("${jdbc.baseline:0}")
    private boolean baseline;

    @Value("${jdbc.baseline-version:-1}")
    private String version;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public boolean isBaseline() {
        return baseline;
    }

    public boolean baselineVersion() {
        return baseline && !(version == null || version.trim().isEmpty() || "-1".equals(version));
    }


    public void setBaseline(boolean baseline) {
        this.baseline = baseline;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public HikariConfig toNative() {
        final HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(this.url);
        cfg.setUsername(this.username);
        cfg.setPassword(this.password);
        cfg.setDriverClassName(this.driverClass);
        return cfg;
    }

}
