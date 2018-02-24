package io.sunshower.persist.core;

import com.arjuna.ats.internal.jdbc.DynamicClass;
import java.sql.SQLException;
import javax.sql.XADataSource;
import org.h2.jdbcx.JdbcDataSource;

public class JtaDynamicClass implements DynamicClass {

  static final Object lock = new Object();
  static volatile XADataSource datasource;

  public static void clear() {
    synchronized (lock) {
      datasource = null;
    }
  }

  @Override
  public XADataSource getDataSource(String dbName) throws SQLException {
    //    if (datasource == null) {
    //      synchronized (lock) {
    //        if (datasource == null) {
    //          final JdbcDataSource dataSource = new JdbcDataSource();
    //          dataSource.setUser("sa");
    //          dataSource.setPassword("");
    //          dataSource.setURL("jdbc:" + dbName);
    //          return (datasource = dataSource);
    //        }
    //      }
    //    }
    //    return datasource;

    final JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUser("sa");
    dataSource.setPassword("");
    dataSource.setURL("jdbc:" + dbName);
    return dataSource;
  }
}
