insert into Login_Database
values(10001,'Sumitabha.123@gmail.com','Sumitabha', 'jtLsPQ==', 'Sumitabha.123@gmail.com_Sumitabha');

insert into Login_Database
values(10002,'Diganta.mohapatra@gmail.com','Diganta', 'ixJcYw==', 'Diganta.mohapatra@gmail.com_Diganta');


insert into Available_Services
values('Sumitabha','Netherlandse Films',10,4.0);

insert into Available_Services
values('Sumitabha','Netherlandse Series',20,6.0);

insert into Available_Services
values('Diganta','Netherlandse Films',8,5.0);

insert into Available_Services
values('Diganta','Netherlandse Series',25,8.0);

insert into Subscribed_Services
values(1001, 'Sumitabha','Netherlandse Films',1,4.0,TO_DATE('07-11-2018', 'DD-MM-YYYY'), TO_DATE('07-11-2018', 'DD-MM-YYYY'));

insert into Subscribed_Services
values(2001, 'Sumitabha','Netherlandse Series',1,6.0,TO_DATE('09-12-2018', 'DD-MM-YYYY'), TO_DATE('09-12-2018', 'DD-MM-YYYY'));

insert into Subscribed_Services
values(3001, 'Diganta','Netherlandse Films',1,5.0,TO_DATE('09-11-2018', 'DD-MM-YYYY'), TO_DATE('09-12-2018', 'DD-MM-YYYY'));

insert into Subscribed_Services
values(4001, 'Diganta','Netherlandse Series',1,8.0,TO_DATE('09-11-2018', 'DD-MM-YYYY'), TO_DATE('09-12-2018', 'DD-MM-YYYY'));