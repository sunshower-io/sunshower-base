package io.sunshower.model.api;

public interface SecurityTables {

  /** the object identity table */
  String ACL_OBJECT_IDENTITY = "acl_object_identity";

  /** the type of a secured object */
  String SECURED_OBJECT_TYPE = "acl_class";

  String SECURITY_IDENTITY = "acl_sid";

  String ACCESS_CONTROL_ENTRY = "acl_entry";

  String USER = "USERS";

  /** constant names for acl_object_identity */
  interface AclObjectIdentityFields {
    String INHERITS_ENTRIES = "entries_inheriting";
    String PARENT_REFERENCE = "parent_object";
    String OBJECT_IDENTITY_TYPE = "object_id_class";
    String OBJECT_IDENTITY_REFERENCE = "object_id_identity";
  }

  /** constant names of acl_sid reference SecurityIdentity */
  interface AclSecurityIdentity {
    String SID = "sid";

    String PRINCIPAL = "principal";
  }

  interface User {
    String USERNAME = "username";
    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String PASSWORD = "password";
  }
}
