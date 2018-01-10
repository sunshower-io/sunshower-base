package io.sunshower.persistence.core;

import io.sunshower.encodings.Base58;
import io.sunshower.encodings.Encoding;
import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

public class IdentifierBridge implements TwoWayFieldBridge {

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
}
