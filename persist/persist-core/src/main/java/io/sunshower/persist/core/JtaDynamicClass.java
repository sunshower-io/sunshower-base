package io.sunshower.persist.core;

import com.arjuna.ats.internal.jdbc.DynamicClass;
import java.sql.SQLException;
import javax.sql.XADataSource;
import org.h2.jdbcx.JdbcDataSource;

public class JtaDynamicClass implements DynamicClass {

  @Override
  public XADataSource getDataSource(String dbName) throws SQLException {
    final JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUser("sa");
    dataSource.setPassword("");
    dataSource.setURL("jdbc:" + dbName);
    return dataSource;
  }
}
