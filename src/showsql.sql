-- drop database if exists book;
-- create database book;
-- use book;

drop table if exists category;
drop table if exists book;
drop table if exists bookpic;

create table category (
  id   varchar(32) primary key,
  name varchar(32)
);

create table book (
  id          varchar(32) primary key,
  name        varchar(32),
  price       double,
  author      varchar(32),
  publishdate datetime,
  categoryid  varchar(32) references category (id),
  status      varchar(5)
);

create table bookpic (
  id       varchar(32) primary key,
  savepath varchar(255),
  showname varchar(100),
  bookid   varchar(32) references book (id),
  fm       varchar(5)
);

-- foreign key (xxid) references(id)