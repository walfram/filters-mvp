--
-- File: schema.sql
-- Description: Defines the database schema for Filter and its polymorphic Criteria
-- Strategy: JOINED Inheritance
--

-- 1. Create the parent 'filter' table
CREATE TABLE filter
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

--------------------------------------------------------------------------------

-- 2. Create the base 'criterion' table
-- This table holds common fields (id) and the foreign key to the filter.
CREATE TABLE criterion
(
    id             VARCHAR(255) PRIMARY KEY,
    filter_id      VARCHAR(255) NOT NULL,
    criterion_type VARCHAR(30)  NOT NULL, -- Discriminator column for easier ORM query

    -- Define the foreign key relationship to the filter table
    CONSTRAINT fk_criterion_filter
        FOREIGN KEY (filter_id)
            REFERENCES filter (id)
);

--------------------------------------------------------------------------------

-- 3. Create the specific 'amount_criterion' table
-- PK/FK (id) references the base 'criterion' table
CREATE TABLE amount_criterion
(
    id       VARCHAR(255) PRIMARY KEY,
    operator VARCHAR(10)      NOT NULL,
    amount_value    DOUBLE PRECISION NOT NULL,

    -- Define the primary key as a foreign key to criterion
    CONSTRAINT fk_amount_criterion
        FOREIGN KEY (id)
            REFERENCES criterion (id)
);

--------------------------------------------------------------------------------

-- 4. Create the specific 'title_criterion' table
-- PK/FK (id) references the base 'criterion' table
CREATE TABLE title_criterion
(
    id             VARCHAR(255) PRIMARY KEY,
    operator       VARCHAR(20)  NOT NULL,
    title_value          VARCHAR(255) NOT NULL,
    case_sensitive BOOLEAN      NOT NULL,

    -- Define the primary key as a foreign key to criterion
    CONSTRAINT fk_title_criterion
        FOREIGN KEY (id)
            REFERENCES criterion (id)
);

--------------------------------------------------------------------------------

-- 5. Create the specific 'date_criterion' table
-- PK/FK (id) references the base 'criterion' table
CREATE TABLE date_criterion
(
    id       VARCHAR(255) PRIMARY KEY,
    operator VARCHAR(10)              NOT NULL,
    -- Using TIMESTAMP WITH TIME ZONE is best practice for Java's Instant/Date
    date_value    TIMESTAMP WITH TIME ZONE NOT NULL,

    -- Define the primary key as a foreign key to criterion
    CONSTRAINT fk_date_criterion
        FOREIGN KEY (id)
            REFERENCES criterion (id)
);
