package io.sunshower.persistence.core.converters;

import io.sunshower.persistence.core.MachineAddress;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MACConverter implements AttributeConverter<MachineAddress, char[]> {
  @Override
  public char[] convertToDatabaseColumn(MachineAddress machineAddress) {
    if (machineAddress == null) {
      return null;
    }
    return machineAddress.getValue();
  }

  @Override
  public MachineAddress convertToEntityAttribute(char[] chars) {
    if (chars == null) {
      return null;
    }
    return new MachineAddress(chars);
  }
}
