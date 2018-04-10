package io.sunshower.persistence.core.converters;

import io.sunshower.persistence.core.NetworkAddress;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IPConverter implements AttributeConverter<NetworkAddress, char[]> {

  @Override
  public char[] convertToDatabaseColumn(NetworkAddress machineAddress) {
    if (machineAddress == null) {
      return null;
    }
    return machineAddress.getValue();
  }

  @Override
  public NetworkAddress convertToEntityAttribute(char[] chars) {
    if (chars == null) {
      return null;
    }
    return new NetworkAddress(chars);
  }
}
