-- Create geo table
CREATE TABLE geo (
    id BIGSERIAL PRIMARY KEY,
    lat VARCHAR(100),
    lng VARCHAR(100)
);

-- Create address table
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    suite VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    zipcode VARCHAR(100) NOT NULL,
    geo_id BIGINT REFERENCES geo(id)
);

-- Create company table
CREATE TABLE company (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    catch_phrase VARCHAR(200) NOT NULL,
    bs VARCHAR(200) NOT NULL
);

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(100),
    website VARCHAR(100),
    address_id BIGINT REFERENCES address(id),
    company_id BIGINT REFERENCES company(id)
);