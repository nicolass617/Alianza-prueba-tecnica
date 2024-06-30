-- Crea la tabla USERS
CREATE TABLE IF NOT EXISTS USERS (
    SharedKey VARCHAR(255) PRIMARY KEY,
    BusinessID VARCHAR(255),
    Email VARCHAR(255),
    Phone VARCHAR(10),
    Start_date DATE,
    End_date DATE,
    Date_added DATE
);

-- Agrega una restricción de unicidad en la columna BusinessID
ALTER TABLE USERS ADD CONSTRAINT UK_BusinessID UNIQUE (BusinessID);