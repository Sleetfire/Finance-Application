CREATE USER "account-service_user" WITH PASSWORD 'TiP22r1nJyyu';
CREATE DATABASE "account-service" WITH OWNER = "account-service_user";
\c "account-service"

CREATE SCHEMA account_service;

CREATE TABLE account_service.balance
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    value numeric NOT NULL,
    CONSTRAINT balance_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS account_service.balance
    OWNER to "account-service_user";

CREATE TABLE account_service.account
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    title character varying NOT NULL,
    description character varying NOT NULL,
    balance_uuid uuid NOT NULL,
    type character varying NOT NULL,
    currency uuid NOT NULL,
    username character varying NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (uuid),
    CONSTRAINT balance_fk FOREIGN KEY (balance_uuid)
        REFERENCES account_service.balance (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS account_service.account
    OWNER to "account-service_user";


CREATE TABLE account_service.operation
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    "date" timestamp without time zone NOT NULL,
    description character varying NOT NULL,
    category uuid NOT NULL,
    value numeric NOT NULL,
    currency uuid NOT NULL,
    account_uuid uuid NOT NULL,
    username character varying NOT NULL,
    CONSTRAINT operation_pk PRIMARY KEY (uuid),
    CONSTRAINT account_fk FOREIGN KEY (account_uuid)
        REFERENCES account_service.account (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS account_service.operation
    OWNER to "account-service_user";