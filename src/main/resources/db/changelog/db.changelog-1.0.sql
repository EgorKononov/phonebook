-- liquibase formatted sql

-- changeset ekononov:1
CREATE TABLE IF NOT EXISTS companies
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(254) UNIQUE NOT NULL
);
-- rollback DROP TABLE companies;

-- changeset ekononov:2
CREATE TABLE IF NOT EXISTS contacts
(
    id           BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    first_name   VARCHAR(64),
    last_name    VARCHAR(64),
    company_id   BIGINT REFERENCES companies (id)
)
-- rollback DROP TABLE contacts;