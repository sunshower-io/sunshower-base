package io.sunshower.common.rs;

import java.util.UUID;
import org.eclipse.persistence.internal.helper.ConversionManager;
import org.eclipse.persistence.internal.oxm.XMLConversionManager;

public class DefaultConversionManager extends XMLConversionManager {
  private DefaultConversionManager() {
    super();
  }

  static class Holder {
    static final ConversionManager INSTANCE = new DefaultConversionManager();
  }

  public static ConversionManager getInstance() {
    return Holder.INSTANCE;
  }

  @Override
  public Object convertObject(Object sourceObject, Class javaClass) {
    if (javaClass == UUID.class) {
      if (sourceObject != null) {
        return UUID.fromString((String) sourceObject);
      }
    }
    return super.convertObject(sourceObject, javaClass);
  }
}
