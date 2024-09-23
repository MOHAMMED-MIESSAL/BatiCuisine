-- ENUM for Project Status :
CREATE TYPE project_status AS ENUM ('InPROGRESS', 'COMPLETED', 'CANCELED');


-- Table client
CREATE TABLE client (
                        id               SERIAL PRIMARY KEY,
                        name             VARCHAR(50) NOT NULL,
                        address          VARCHAR(50),
                        phone            VARCHAR(20),
                        is_professional  BOOLEAN NOT NULL
);

-- Table project
CREATE TABLE project (
                         id             SERIAL PRIMARY KEY,
                         name           VARCHAR(50) NOT NULL,
                         profit_margin  NUMERIC(10, 2),
                         total_cost     NUMERIC(10, 2),
                         state_project  project_status,
                         client_id      INTEGER REFERENCES client (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table component
CREATE TABLE component (
                           id              SERIAL PRIMARY KEY,
                           name            VARCHAR(50) NOT NULL,
                           type_component  VARCHAR(20) NOT NULL, -- Material || manPower
                           vat_rate        NUMERIC(5, 2),
                           project_id      INTEGER REFERENCES project(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table material
CREATE TABLE material (
                          id                 SERIAL PRIMARY KEY,
                          unit_cost          NUMERIC(10, 2),
                          quantity           NUMERIC(10, 2) CHECK (quantity >= 0),
                          transport_cost     NUMERIC(10, 2),
                          coefficient_quality NUMERIC(5, 2)
) INHERITS (component);

-- Table manPower
CREATE TABLE labor (
                       id                  SERIAL PRIMARY KEY,
                       hourly_rate         NUMERIC(10, 2),
                       hours_work          NUMERIC(10, 2),
                       worker_productivity  NUMERIC(5, 2)
) INHERITS (component);

-- Table quotes
CREATE TABLE estimate (
                          id              SERIAL PRIMARY KEY,
                          amount_estimate NUMERIC(10, 2),
                          date_emission   DATE,
                          date_validity   DATE,
                          is_accepted     BOOLEAN,
                          project_id      INTEGER REFERENCES project(id) ON DELETE CASCADE ON UPDATE CASCADE
);



ALTER TABLE project
    ALTER COLUMN state_project SET DEFAULT 'InPROGRESS'::project_status;
