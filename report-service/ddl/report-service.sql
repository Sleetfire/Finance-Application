CREATE USER "report-service_user" WITH PASSWORD 'CLlOg42MYtKt';
CREATE DATABASE "report-service" WITH OWNER = "report-service_user";
\c "report-service"

CREATE SCHEMA report_service;

CREATE TABLE report_service.report
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    status character varying NOT NULL,
    type character varying NOT NULL,
    description character varying NOT NULL,
    params character varying NOT NULL,
    url character varying,
    username character varying NOT NULL,
    CONSTRAINT report_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS report_service.report
    OWNER to "report-service_user";