drop table T_ORDER if exists;

create table T_ORDER (ID bigint identity primary key, NUMBER varchar(9),
                        CLIENT varchar(50) not null, TOTAL decimal(8,2), unique(NUMBER));
                        
ALTER TABLE T_ORDER ALTER COLUMN TOTAL SET DEFAULT 0.0;
