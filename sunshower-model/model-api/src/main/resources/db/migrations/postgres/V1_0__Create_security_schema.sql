CREATE TABLE TENANTS
(

    id          BINARY(16) NOT NULL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    created     TIMESTAMP default NOW(),
    icon        bytea,
    type        varchar(63),

    parent_id   BINARY(16),

    CONSTRAINT tenant_to_parent_reference
        FOREIGN KEY (parent_id)
            REFERENCES TENANTS (id)
);


CREATE TABLE USERS
(

    /**
      the id
     */
    id                    BINARY(16)   NOT NULL PRIMARY KEY,

    tenant_id             BINARY(16),

    /**
      the username
     */
    username              VARCHAR(255) NOT NULL,

    /**
      when was this user created
     */
    created               TIMESTAMP default NOW(),


    /**
      when was this user last logged on
     */
    last_authenticated    TIMESTAMP,


    locked                BOOLEAN   DEFAULT FALSE,

    expired               BOOLEAN   DEFAULT FALSE,

    salt                  BINARY(32),

    initialization_vector BINARY(16),


    /**
      the password
     */
    password              VARCHAR(255) NOT NULL,

    /**
      unique constraint on username
     */
    CONSTRAINT
        system_user_unique_username
        UNIQUE (username),

    CONSTRAINT users_to_tenant_ref
        FOREIGN KEY (tenant_id)
            REFERENCES TENANTS (id)


);

CREATE TABLE TENANTS_TO_USERS
(
    tenant_id BINARY(16),
    user_id   BINARY(16),
    CONSTRAINT
        tenants_to_users_user_ref
        FOREIGN KEY (user_id)
            REFERENCES USERS (id),

    CONSTRAINT
        tenants_to_users_tenant_ref
        FOREIGN KEY (tenant_id)
            REFERENCES TENANTS (id)
);

CREATE TABLE USER_DETAILS
(
    id         BINARY(16)   NOT NULL PRIMARY KEY,
    icon       bytea,
    type        varchar(63),
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,

    CONSTRAINT
        user_details_to_user
        FOREIGN KEY (id)
            REFERENCES
                USERS (id)

);

CREATE UNIQUE INDEX
    system_user_username_idx
    on USERS (username);

CREATE TABLE acl_sid
(
    id        binary(16)   NOT NULL PRIMARY KEY,
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
    id    BINARY(16)   NOT NULL PRIMARY KEY,
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
    id                 BINARY(16) NOT NULL PRIMARY KEY,

    /**
      the owner security id for this OID

     */
    owner_sid          BINARY(16),

    /**
      reference to a parent type--may be null
    */
    parent_object      BINARY(16),

    /**
      reference the type of the secured object by ID
     */
    object_id_class    BINARY(16) NOT NULL,

    /**
      the ID of the referenced object
     */
    object_id_identity BINARY(16) NOT NULL,

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
    id                  BINARY(16) NOT NULL PRIMARY KEY,

    /**
      reference  acl_object_identity
     */
    acl_object_identity BINARY(16) NOT NULL,


    /**
      the access control entry order
     */

    ace_order           INTEGER    NOT NULL,

    /**
      reference the acl_sid (secured object)
     */
    sid                 BINARY(16) NOT NULL,


    mask                INTEGER    NOT NULL,

    granting            BOOLEAN    NOT NULL,

    audit_success       BOOLEAN    NOT NULL,
    audit_failure       BOOLEAN    NOT NULL,

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
                acl_object_identity (id),


    CONSTRAINT
        acl_entry_acl_sid_ref
        FOREIGN KEY (sid)
            REFERENCES acl_sid (id)
);

/**
  maps to io.sunshower.model.api.Permission
 */
CREATE TABLE PERMISSIONS
(

    id                      BINARY(16)   NOT NULL PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    grantee_id              BINARY(16),
    access_control_entry_id BINARY(16),

    CONSTRAINT permission_unique_name
        UNIQUE (name),

    CONSTRAINT permission_grantee_reference
        FOREIGN KEY
            (grantee_id)
            REFERENCES
                USERS (id),


    CONSTRAINT permission_ace_reference
        FOREIGN KEY (access_control_entry_id)
            REFERENCES acl_entry (id)
);

CREATE TABLE ROLES
(
    id   BINARY(16) PRIMARY KEY,
    name VARCHAR(255)
);


CREATE TABLE ROLES_TO_USERS
(
    role_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    CONSTRAINT
        roles_to_users_ref_role
        FOREIGN KEY (role_id)
            REFERENCES ROLES (id),

    CONSTRAINT
        roles_to_users_ref_user
        FOREIGN KEY (user_id)
            REFERENCES USERS (id)

);


CREATE TABLE GROUPS
(
    id          BINARY(16) PRIMARY KEY,


    name        varchar(255),
    description varchar(255),

    icon        bytea,
    media_type  VARCHAR(63)
);

CREATE TABLE GROUPS_TO_USERS
(
    user_id  BINARY(16),
    group_id BINARY(16),
    CONSTRAINT
        groups_to_users_user_ref
        FOREIGN KEY (user_id)
            REFERENCES USERS (id),

    CONSTRAINT
        groups_to_users_group_ref
        FOREIGN KEY (user_id)
            REFERENCES GROUPS (id)
);

CREATE TABLE PERMISSIONS_TO_USERS
(
    user_id       BINARY(16),
    permission_id BINARY(16),
    CONSTRAINT
        permissions_to_users_user_ref
        FOREIGN KEY (user_id)
            REFERENCES USERS (id),

    CONSTRAINT
        permissions_to_users_permission_ref
        FOREIGN KEY (permission_id)
            REFERENCES PERMISSIONS (id)
);

CREATE TABLE REALMS
(
    id       BINARY(16) NOT NULL PRIMARY KEY,
    name     VARCHAR(255),
    provider VARCHAR(255)
);


CREATE TABLE REALM_TO_USERS
(
    realm_id BINARY(16) NOT NULL,
    user_id  BINARY(16) NOT NULL,
    CONSTRAINT realm_to_users_realm_ref
        FOREIGN KEY (realm_id)
            REFERENCES REALMS (id)

);

