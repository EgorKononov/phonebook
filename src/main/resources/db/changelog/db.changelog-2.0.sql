-- liquibase formatted sql

-- changeset ekononov:1
ALTER TABLE contacts
    ADD COLUMN birth_date DATE,
    ADD COLUMN email VARCHAR(254) UNIQUE;