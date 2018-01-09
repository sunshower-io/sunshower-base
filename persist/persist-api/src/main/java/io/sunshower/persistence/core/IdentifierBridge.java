package io.sunshower.persistence.core;

import io.sunshower.encodings.Base58;
import io.sunshower.encodings.Encoding;
import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;
import org.hibernate.search.bridge.spi.EncodingBridge;
import org.hibernate.search.bridge.spi.IgnoreAnalyzerBridge;
import org.hibernate.search.bridge.spi.NullMarker;
import org.hibernate.search.bridge.util.impl.ToStringNullMarker;
import org.hibernate.search.metadata.NumericFieldSettingsDescriptor;

public class IdentifierBridge implements IgnoreAnalyzerBridge, EncodingBridge, TwoWayFieldBridge {

  static final Encoding encoding = Base58.getInstance(Base58.Alphabets.Default);

  @Override
  public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
    if (value != null) {
      byte[] v = (byte[]) value;
      luceneOptions.addFieldToDocument(name, encoding.encode(v), document);
    }
  }

  @Override
  public Object get(String name, Document document) {
    String data = document.getField(name).stringValue();
    return encoding.decode(data);
  }

  @Override
  public String objectToString(Object object) {
    if (object == null) {
      return null;
    }
    return object.toString();
  }

  @Override
  public NumericFieldSettingsDescriptor.NumericEncodingType getEncodingType() {
    return NumericFieldSettingsDescriptor.NumericEncodingType.UNKNOWN;
  }

  @Override
  public NullMarker createNullMarker(String indexNullAs) throws NumberFormatException {
    return new ToStringNullMarker("");
  }
}
