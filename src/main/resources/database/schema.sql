create table sample (  
    id int not null IDENTITY,  
    name varchar(80),  
    address varchar(200),
    phone varchar (13),
    age int,
    constraint pk_sample primary key (id)  
);  

create table address (  
    id int not null IDENTITY,  
    address varchar(200),
    city varchar (13),
    remark varchar(300),
    constraint pk_address primary key (id)  
);  

create table regions (  
    id int not null IDENTITY,  
    address varchar(200),
    city varchar (13),
    remark varchar(300),
    constraint pk_regions primary key (id)  
); 