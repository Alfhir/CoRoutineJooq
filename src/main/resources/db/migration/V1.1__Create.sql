-- V1.1__Create.sql

-- Create the schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS demo;

-- Drop all tables --
drop table if exists demo.language;

-- Create the "language" table
CREATE TABLE demo.language
(
    language_id SERIAL PRIMARY KEY,
    name        VARCHAR(30)
);
