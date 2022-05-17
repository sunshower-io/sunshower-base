CREATE TABLE SUNSHOWER_USER
(

    /**
      the id
     */
    id BINARY (16) NOT NULL PRIMARY KEY,

    /**
      the username
     */
    username VARCHAR(255) NOT NULL,

    /**
      the password
     */
    password VARCHAR(255) NOT NULL,

    /**
      unique constraint on username
     */
    CONSTRAINT
        system_user_unique_username
        UNIQUE (username)
);

CREATE UNIQUE INDEX
    system_user_username_idx
    on SUNSHOWER_USER (username);

CREATE TABLE acl_sid
(
    id binary (16) NOT NULL PRIMARY KEY,
    principal BOOLEAN      NOT NULL,
    sid       varchar(128) NOT NULL,

    CONSTRAINT unique_principal_sid
        UNIQUE (sid, principal)
);

/**

  relates a type to an object-identity
  see io.sunshower.model.api.SecuredType
 */
CREATE TABLE acl_class
(
    id BINARY (16) NOT NULL PRIMARY KEY,
    class VARCHAR(128) NOT NULL,
    CONSTRAINT
        unique_type
        UNIQUE (class)
);

/**
  acl_object_identity relates
  an id and a class to an acl
  see io.sunshower.model.api.ObjectIdentity
 */
CREATE TABLE acl_object_identity
(

    /**
      the ID for this object identity
     */
    id BINARY (16) NOT NULL PRIMARY KEY,

    /**
      the owner security id for this OID

     */
    owner_sid BINARY (16),

    /**
      reference to a parent type--may be null
    */
    parent_object BINARY (16),

    /**
      reference the type of the secured object by ID
     */
    object_id_class BINARY (16) NOT NULL,

    /**
      the ID of the referenced object
     */
    object_id_identity BINARY (16) NOT NULL,

    entries_inheriting BOOLEAN,


    /**
     ensure that this oid is unique across the type and the identity
    */

    CONSTRAINT
        unique_type_and_id
        UNIQUE (
                object_id_class,
                object_id_identity
            ),

    /**
      self-reference constraint back to self (for parent-child relationship)
     */
    CONSTRAINT parent_id_reference
        FOREIGN KEY (parent_object)
            REFERENCES acl_object_identity (id),


    /**
      reference secured type
     */
    CONSTRAINT secured_type_reference
        FOREIGN KEY (object_id_class)
            REFERENCES acl_class (id),


    CONSTRAINT owner_sid_reference
        FOREIGN KEY (owner_sid)
            REFERENCES acl_sid (id)
);

/**
  references io.sunshower.model.api.AccessControlEntry
 */
CREATE TABLE acl_entry
(

    /**
      primary key
     */
    id BINARY (16) NOT NULL PRIMARY KEY,

    /**
      reference  acl_object_identity
     */
    acl_object_identity BINARY (16) NOT NULL,


    /**
      the access control entry order
     */

    ace_order     INTEGER NOT NULL,

    /**
      reference the acl_sid (secured object)
     */
    sid BINARY (16) NOT NULL,


    mask          INTEGER NOT NULL,

    granting      BOOLEAN NOT NULL,

    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,

    CONSTRAINT
        acl_entry_unique_id_order
        UNIQUE (
                acl_object_identity,
                ace_order
            ),


    CONSTRAINT
        acl_entry_acl_object_identity_ref
    FOREIGN KEY
        (acl_object_identity)
    REFERENCES
        acl_object_identity(id),


    CONSTRAINT
        acl_entry_acl_sid_ref
    FOREIGN KEY (sid)
    REFERENCES acl_sid(id)
);