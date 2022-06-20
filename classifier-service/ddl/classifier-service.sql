CREATE USER "classifier-service_user" WITH PASSWORD 'kswDW9g1dbfA';
CREATE DATABASE "classifier-service" WITH OWNER = "classifier-service_user";
\c "classifier-service"

CREATE SCHEMA "classifier_service"

CREATE TABLE classifier_service.currency
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    title character varying NOT NULL,
    description character varying NOT NULL,
    CONSTRAINT currency_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS classifier_service.currency
    OWNER to "classifier-service_user";


CREATE TABLE classifier_service.operation_category
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update time without time zone NOT NULL,
    title character varying NOT NULL,
    CONSTRAINT operation_category_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS classifier_service.operation_category
    OWNER to "classifier-service_user";