create table Login_Database
(
   id integer not null,
   Email_id varchar(255) not null,
   Name varchar(255) not null,
   Password varchar(255) not null,
   Autoization_Key varchar2(250 Char) not null,
   primary key(id),
   CONSTRAINT unk_Email_id UNIQUE (Email_id)
);

create table Available_Services
(
   User_Name varchar(255) not null,
   Name varchar(255) not null,
   Available_Content integer not null,
   Price decimal(4,1) not null
);

create table Subscribed_Services
(
    id integer not null,
   User_Name varchar(255) not null,
   Name varchar(255) not null,
   Subscribed_Content integer not null,
   Price decimal(4,1) not null,
   Subscribed_Date Date not null,
   Start_Date Date not null
);
