DROP TABLE IF EXISTS filter_conditions;
DROP TABLE IF EXISTS filters;

CREATE TABLE filters
(
    id     VARCHAR(36) PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    active BOOLEAN      NOT NULL
);

CREATE TABLE filter_conditions
(
    id                VARCHAR(36) PRIMARY KEY,
    filter_id         VARCHAR(36) NOT NULL,
    condition_type    VARCHAR(10) NOT NULL,
    condition_details JSON        NOT NULL,
    FOREIGN KEY (filter_id) REFERENCES filters (id) ON DELETE CASCADE
);
