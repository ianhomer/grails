package org.codehaus.groovy.grails.orm.hibernate.cfg;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public class GormContext {
  private static final NamingStrategy DEFAULT_NAMING_STRATEGY = ImprovedNamingStrategy.INSTANCE;
  private static final Map<Class<?>, Mapping> MAPPING_CACHE = new HashMap<Class<?>, Mapping>();
  private static GormContext singleton;

  static {
    singleton = new GormContext();
    singleton.setNamingStrategy(DEFAULT_NAMING_STRATEGY);
  }

  private static ThreadLocal<GormContext> instance = new ThreadLocal<GormContext>();

  private NamingStrategy namingStrategy;

  public static GormContext get() {
    GormContext gormContext = instance.get();
    if (instance == null) {
      gormContext = new GormContext();
      instance.set(gormContext);
    }
    return instance.get();
  }

  public static void destroy() {
    instance.set(null);
  }

  public void setNamingStrategy(NamingStrategy namingStrategy) {
    this.namingStrategy = namingStrategy;
  }

  public NamingStrategy getNamingStrategy() {
    return namingStrategy;
  }

  /*
   * Cache mapping against class. Exposed so that mapping caching can be
   * customised externally.
   */
  public void putMapping(Class<?> clazz, Mapping mapping) {
    MAPPING_CACHE.put(clazz, mapping);
  }

  /*
   * Get cached mapping for class. Exposed so that mapping caching can be
   * customised externally.
   */
  public Mapping getMapping(Class<?> clazz) {
    return MAPPING_CACHE.get(clazz);
  }

}
