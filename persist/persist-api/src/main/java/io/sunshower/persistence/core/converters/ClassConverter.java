package io.sunshower.persistence.core.converters;

import javax.persistence.AttributeConverter;

public class ClassConverter implements AttributeConverter<Class<?>, String> {
  @Override
  public String convertToDatabaseColumn(Class<?> attribute) {
    return attribute != null ? attribute.getName() : Void.class.getName();
  }

  @Override
  public Class<?> convertToEntityAttribute(String dbData) {
    if (dbData != null) {
      try {
        return Class.forName(dbData);
      } catch (Exception ex) {

      }
    }
    return Void.class;
  }
}
