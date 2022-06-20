CREATE USER "user-service_user" WITH PASSWORD 'CLlOg42MYtKt';
CREATE DATABASE "user-service" WITH OWNER = "user-service_user";
\c "user-service"

CREATE SCHEMA user_service;

CREATE TABLE user_service."user"
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    name character varying NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    role character varying NOT NULL,
    enabled boolean NOT NULL,
    verify_code character varying,

    PRIMARY KEY (uuid),
    UNIQUE (username)
);

ALTER TABLE IF EXISTS user_service."user"
    OWNER to "user-service_user";