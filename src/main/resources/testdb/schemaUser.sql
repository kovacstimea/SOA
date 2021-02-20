drop table T_USER if exists;

create table T_USER (ID bigint identity primary key, USERNAME varchar(50) not null,
                    NUMBER varchar(9), PASSWORD varchar(50) not null, unique(USERNAME,NUMBER));

