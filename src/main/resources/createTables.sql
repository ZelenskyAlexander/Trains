drop table if exists Driver cascade;
drop table if exists Passenger cascade;
drop table if exists Freight cascade;
drop table if exists Train cascade;

create table Driver (
    Id int primary key,
    FirstName text not null,
    LastName text not null
);

create table Passenger (
    Id int primary key,
    Producer text not null,
    NumPassenger int not null,
    Type text not null
);

create table Freight (
    Id int primary key,
    Producer text not null,
    MaxCapacity int not null,
    Type text not null
);

create table Train (
    Id int not null,
    Wagon int not null,
    Driver int not null,
    Type text not null
);