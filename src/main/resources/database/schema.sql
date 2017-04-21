create table sample (  
    id int not null IDENTITY,  
    name varchar(80),  
    address varchar(200),
    phone varchar (13),
    age int,
    constraint pk_sample primary key (id)  
);  