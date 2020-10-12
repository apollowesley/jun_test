package net.oschina.j2cache.hibernate4;

import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cfg.Settings;

import net.oschina.j2cache.J2Cache;


public class J2CacheRegionFactory extends AbstractJ2CacheRegionFactory {


	private static final long serialVersionUID = -8783830401340610234L;

	public J2CacheRegionFactory() {
    }

    public J2CacheRegionFactory(Properties prop) {
        super();
    }

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        this.settings = settings;
        if (this.channel == null) {
            this.channel = J2Cache.getChannel();
        }
    }

    @Override
    public void stop() {
        if (channel != null) {
            channel.close();
        }
    }

}