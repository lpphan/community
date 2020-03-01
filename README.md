## 简介

## 信息

## SQL
```sql
create table USER
(
    ID           INTEGER default NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_E8C1233C_FAFC_4976_8ED5_77C6C5F2905C" auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        VARCHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```
