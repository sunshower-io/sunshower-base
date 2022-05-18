package io.sunshower.model.api;

import io.sunshower.arcus.condensation.Converter;
import java.util.Date;

public class DateConverter implements Converter<Date, Long> {

  @Override
  public Date read(Long l) {
    return new Date(l);
  }

  @Override
  public Long write(Date date) {
    if (date == null) {
      return null;
    }
    return date.getTime();
  }
}
