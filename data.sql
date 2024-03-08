create table accounts (
    accountnumber int not null,
    balance int not null,
    customerid int,
    phone int not null,
    ifsc varchar(255),
    name varchar(255),
    oldpassword varchar(255),
    password varchar(255),
    type varchar(255),
    constraint unique_account_number unique (accountnumber)
);

-- refered from https://docs.oracle.com/cd/b28359_01/server.111/b28286/statements_6015.htm
create sequence customerid_seq start with 1 increment by 1;

create or replace trigger accounts_trigger
before insert on accounts
for each row
begin
    if :new.customerid is null then
        select customerid_seq.nextval into :new.customerid from dual;
    end if;
end;
/

create table transaction (
    amount int not null,
    fromaccount int not null,
    id int,
    toaccount int not null,
    ifsc varchar(255)
);

-- refered from https://docs.oracle.com/cd/b28359_01/server.111/b28286/statements_6015.htm
create sequence transaction_id_seq start with 1 increment by 1;

create or replace trigger transaction_trigger
before insert on transaction
for each row
begin
    if :new.id is null then
        select transaction_id_seq.nextval into :new.id from dual;
    end if;
end;
/