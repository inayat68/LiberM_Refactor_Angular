create extension pgcrypto;

CREATE SCHEMA ONLINE_SECURITY;

SET SCHEMA 'online_security';

CREATE TABLE RDBMS_AUTHENTICATION_PROVIDER_USERS (
	USERID		VARCHAR(8) NOT NULL PRIMARY KEY,
	USERNAME 	VARCHAR(20) NOT NULL,
	PASSWD		VARCHAR(512) NOT null,
	ROLES       VARCHAR(512) not null,
	OPID        CHAR(3),
	OPCLASS     CHAR(3),
	EXPIRES     TIMESTAMP
);

insert into RDBMS_AUTHENTICATION_PROVIDER_USERS values('ADMIN','Administrator', public.crypt('admin', public.gen_salt('bf', 8)), 'admin', 'ADM', 'ABC');
insert into RDBMS_AUTHENTICATION_PROVIDER_USERS values('CICSUSER','CICS Default User', public.crypt('CICSUSER', public.gen_salt('bf', 8)), 'guest', NULL, NULL);
insert into RDBMS_AUTHENTICATION_PROVIDER_USERS values('LEMMY','Lemmy Kilmister', public.crypt('rockout', public.gen_salt('bf', 8)), 'user,operator', NULL, NULL);
insert into RDBMS_AUTHENTICATION_PROVIDER_USERS values('OZZY','Ozzy Osbourne', public.crypt('paranoid', public.gen_salt('bf', 8)), 'operator', NULL, NULL);

CREATE TABLE RDBMS_AUTHENTICATION_PROVIDER_CONFIG (
	PROPERTY_NAME	VARCHAR(512) NOT NULL PRIMARY KEY,
	PROPERTY_VALUE  VARCHAR(512)
);
insert into RDBMS_AUTHENTICATION_PROVIDER_CONFIG values('password.expire','true');
insert into RDBMS_AUTHENTICATION_PROVIDER_CONFIG values('password.lifetime','90');
