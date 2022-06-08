package io.sunshower.model.api;

import io.sunshower.arcus.condensation.Converter;
import java.util.Date;

public class DateConverter implements Converter<Date, String> {

  @Override
  public Date read(String l) {
    if (l == null) {
      return null;
    }
    return new Date(Long.parseLong(l));
  }

  @Override
  public String write(Date date) {
    if (date == null) {
      return null;
    }
    return Long.toString(date.getTime());
  }
}
