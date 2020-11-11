create sequence employee_id_sequence start with 1 increment by 1;

create table employee_new 
(id int default (NEXT VALUE FOR employee_id_sequence) not null,
email varchar(255) unique,
first_name varchar(255),
last_name varchar(255),
primary key (id));
