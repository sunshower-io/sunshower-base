package io.sunshower.common.ws;

import io.sunshower.common.Identifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class IdentifierParameterConverter implements ParamConverterProvider {

  @Override
  @SuppressWarnings("unchecked")
  public <T> ParamConverter<T> getConverter(
      Class<T> rawType, Type genericType, Annotation[] annotations) {
    if (Identifier.class.equals(rawType)) {
      return (ParamConverter<T>) new IdentifierParamConverter();
    }
    return null;
  }

  private static class IdentifierParamConverter implements ParamConverter<Identifier> {

    @Override
    public Identifier fromString(String value) {
      if (value == null) {
        return null;
      }
      return Identifier.valueOf(value);
    }

    @Override
    public String toString(Identifier value) {
      if (value == null) {
        return null;
      }
      return value.toString();
    }
  }
}
