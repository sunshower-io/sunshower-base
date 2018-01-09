package io.sunshower.common.rs;

import org.eclipse.persistence.descriptors.ClassExtractor;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

public class TypeAttributeClassExtractor extends ClassExtractor {

  @Override
  public Class extractClassFromRow(Record databaseRow, Session session) {
    final String type = (String) databaseRow.get("@type");
    if (type != null) {
      try {
        return Class.forName(type);
      } catch (ClassNotFoundException e) {
      }
    }
    throw new IllegalStateException(
        "Did not specify attribute @type or it was not bound to an existing class: " + type);
  }
}
