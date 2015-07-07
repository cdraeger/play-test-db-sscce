# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table domain_model (
  id                        bigint not null,
  unique_row                varchar(255) not null,
  constraint uq_domain_model_unique_row unique (unique_row),
  constraint pk_domain_model primary key (id))
;

create sequence domain_model_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists domain_model;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists domain_model_seq;

