create schema if not exists `computer-database-db`;
use `computer-database-db`;

#-- Company table --

drop table if exists company;
create table company (
  id                        bigint not null auto_increment,
  name                      varchar(255) default null,
  constraint pk_company primary key (id))
;

#-- Computer table --

drop table if exists computer;
create table computer (
  id                        bigint not null auto_increment,
  name                      varchar(255) default null,
  introduced                timestamp null default null,
  discontinued              timestamp null default null,
  company_id                bigint default null,
  constraint pk_computer primary key (id))
;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);

#-- DB_log table --

drop table if exists db_log;
create table db_log (
  id                        bigint not null auto_increment,
  action                    varchar(255) default null,
  date                      timestamp null default null,
  description               varchar(255) default null,
  constraint pk_db_log primary key (id))
;



#-----------------------------------
#--    USER RIGHTS MANAGEMENT     --
#-----------------------------------

#-- User-role table --

drop table if exists user_role;
create table user_role (
  id                        bigint not null auto_increment,
  role                      varchar(30),
  constraint pk_user_role primary key (id))
;

#-- Users table --

drop table if exists users;
create table users (
  id                        bigint not null auto_increment,
  username                      varchar(255),
  password                  varchar(255),
  enabled							tinyint(1),
  role_id                   bigint,
  constraint pk_users primary key (id),
  constraint fk_users_user_role foreign key (role_id) references user_role (id) on delete restrict on update restrict)
;

#-----------------------------------
# -- USER RIGHTS MANAGEMENT
#-----------------------------------
CREATE USER 'jee-cdb'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'jee-cdb'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;
