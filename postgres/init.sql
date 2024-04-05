-- ###################################################################
-- Not needed for the docker Postgres instance
-- ###################################################################

-- Create the role for the database
-- CREATE USER "pet-store" WITH LOGIN SUPERUSER PASSWORD 'pa55w0rd';

-- Create the database
-- CREATE DATABASE "pet-store" WITH OWNER = "pet-store";

-- ###################################################################
-- Create the table
-- ###################################################################

CREATE TABLE IF NOT EXISTS pets
(
    id          bigint       NOT NULL,
    name        varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    age         integer      NOT NULL,
    is_deleted  boolean      NOT NULL DEFAULT false,
    CONSTRAINT pets_primary_key PRIMARY KEY (id)
);
GRANT ALL ON TABLE pets TO "pet-store";

-- ###################################################################
-- Create the sequence
-- ###################################################################

CREATE SEQUENCE IF NOT EXISTS pet_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
GRANT ALL ON SEQUENCE pet_id_seq TO "pet-store";

