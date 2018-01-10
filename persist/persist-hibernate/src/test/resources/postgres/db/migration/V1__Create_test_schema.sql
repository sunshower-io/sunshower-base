


create table TS_V_ENTITY (
  id binary(16) primary key,
  name varchar(52),
  created   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ,
  updated   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP
);


create table TestEntity (

  id          binary(16) primary key,
  parent_id   binary(16),
  name        varchar(20)
);

create table Ownee (
  id        binary(16) primary key,
  name      varchar(100)
);

create table Owner (
  id          binary(16) primary key,
  ownee_id    binary(16),
  name        varchar(1200),
  foreign key (ownee_id) references ownee(id),
);



create table one2many_owner (
    id binary(16) primary key,

);

create table one2many_owned(
  id binary(16) primary key,
  owner_id binary(16),

  foreign key (owner_id) references one2many_owner(id)
);

create table TAG (

  id binary(16) primary key,
  name varchar(22)
);


create table BLOG_ENTRY (
  id binary(16) primary key
);

create table BLOGS_TO_TAGS (
  entry_id binary(16),
  tag_id binary(16),

  foreign key (entry_id) references BLOG_ENTRY(id),
  foreign key (tag_id) references TAG(id)
);


create table INDEXED_ENTITY (

    id        binary(16) primary key,
    name      varchar(256),

    description varchar(256),


    text        varchar(512)
);

create table HIER_PERSON (
    id binary(16) primary key,

    parent_id binary(16),

    foreign key (parent_id) references HIER_PERSON(id)
);

create schema TEST_SCHEMA;

create table TEST_SCHEMA.Entity (
  id binary(16) primary key,
  name varchar(255)
)