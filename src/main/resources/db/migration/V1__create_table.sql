-- ROLE TABLE
CREATE TABLE role (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);

-- PERMISSION ENUM TYPE
DO $$ BEGIN
CREATE TYPE permission AS ENUM (
        'CREATE_USER',
        'READ_USER',
        'UPDATE_USER',
        'DELETE_USER'
    );
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- ROLE_PERMISSION TABLE
CREATE TABLE role_permission (
                                 id BIGSERIAL PRIMARY KEY,
                                 role_id BIGINT,
                                 permission permission NOT NULL,
                                 CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

-- USERS TABLE
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE,
                       username VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       role_id BIGINT,
                       active BOOLEAN DEFAULT true,
                       created_by VARCHAR(255),
                       created_date TIMESTAMP,
                       last_modified_by VARCHAR(255),
                       last_modified_date TIMESTAMP,
                       CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE SET NULL
);

-- INSERT ROLES
INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');

-- INSERT PERMISSIONS FOR ADMIN (all permissions)
INSERT INTO role_permission (role_id, permission) VALUES (1, 'CREATE_USER');
INSERT INTO role_permission (role_id, permission) VALUES (1, 'READ_USER');
INSERT INTO role_permission (role_id, permission) VALUES (1, 'UPDATE_USER');
INSERT INTO role_permission (role_id, permission) VALUES (1, 'DELETE_USER');




-- INSERT PERMISSIONS FOR USER (only READ)
INSERT INTO role_permission (role_id, permission) VALUES (2, 'READ_USER');
INSERT INTO role_permission (role_id, permission) VALUES (2, 'UPDATE_USER');