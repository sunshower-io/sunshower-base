package io.sunshower.ignite.core;

import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cfg.Settings;

import javax.cache.CacheException;
import java.util.Properties;

/**
 * Created by haswell on 5/3/17.
 */
@SuppressWarnings("deprecation")
public class IgniteHibernateRegionFactory extends HibernateRegionFactory {

    public void start(SessionFactoryOptions settings, Properties properties) throws CacheException {
        start(new Settings(settings), properties);
    }


}
