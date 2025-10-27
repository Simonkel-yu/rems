-- ============================================================
-- REMS - Real Estate Management System
-- Database Schema  |  Sprint 0
-- ============================================================

CREATE DATABASE IF NOT EXISTS rems_db;
USE rems_db;

CREATE TABLE IF NOT EXISTS malls (
    mall_id     INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    city        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    full_name     VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(64)  NOT NULL,
    role          ENUM('TENANT','AGENT','ADMIN') NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS units (
    unit_id      INT AUTO_INCREMENT PRIMARY KEY,
    mall_id      INT NOT NULL,
    unit_number  VARCHAR(20) NOT NULL,
    size_sqft    DECIMAL(10,2) NOT NULL,
    base_rate    DECIMAL(10,2) NOT NULL,
    tier         TINYINT NOT NULL CHECK (tier BETWEEN 1 AND 4),
    usage_type   VARCHAR(100),
    status       ENUM('AVAILABLE','LEASED','MAINTENANCE') DEFAULT 'AVAILABLE',
    FOREIGN KEY (mall_id) REFERENCES malls(mall_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS appointments (
    appt_id     INT AUTO_INCREMENT PRIMARY KEY,
    unit_id     INT NOT NULL,
    tenant_id   INT NOT NULL,
    agent_id    INT NOT NULL,
    start_time  DATETIME NOT NULL,
    end_time    DATETIME NOT NULL,
    status      ENUM('PENDING','CONFIRMED','CANCELLED') DEFAULT 'PENDING',
    FOREIGN KEY (unit_id)   REFERENCES units(unit_id) ON DELETE CASCADE,
    FOREIGN KEY (tenant_id) REFERENCES users(user_id),
    FOREIGN KEY (agent_id)  REFERENCES users(user_id),
    CONSTRAINT chk_times CHECK (end_time > start_time)
);

CREATE TABLE IF NOT EXISTS leases (
    lease_id          INT AUTO_INCREMENT PRIMARY KEY,
    unit_id           INT NOT NULL UNIQUE,
    tenant_id         INT NOT NULL,
    start_date        DATE NOT NULL,
    end_date          DATE NOT NULL,
    payment_frequency ENUM('MONTHLY','QUARTERLY','BI_ANNUAL','ANNUAL') NOT NULL,
    status            ENUM('ACTIVE','EXPIRED','TERMINATED') DEFAULT 'ACTIVE',
    FOREIGN KEY (unit_id)   REFERENCES units(unit_id),
    FOREIGN KEY (tenant_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS invoices (
    invoice_id           INT AUTO_INCREMENT PRIMARY KEY,
    lease_id             INT NOT NULL,
    billing_period       DATE NOT NULL,
    base_rent            DECIMAL(10,2) NOT NULL,
    water_charge         DECIMAL(10,2) DEFAULT 0.00,
    electricity_charge   DECIMAL(10,2) DEFAULT 0.00,
    waste_charge         DECIMAL(10,2) DEFAULT 0.00,
    total_amount         DECIMAL(10,2) NOT NULL,
    status               ENUM('UNPAID','PAID','OVERDUE') DEFAULT 'UNPAID',
    issued_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (lease_id) REFERENCES leases(lease_id)
);

CREATE TABLE IF NOT EXISTS maintenance_requests (
    ticket_id     INT AUTO_INCREMENT PRIMARY KEY,
    unit_id       INT NOT NULL,
    tenant_id     INT NOT NULL,
    description   TEXT NOT NULL,
    priority      ENUM('EMERGENCY','ROUTINE') NOT NULL,
    status        ENUM('OPEN','IN_PROGRESS','CLOSED') DEFAULT 'OPEN',
    chargeable    BOOLEAN DEFAULT FALSE,
    charge_amount DECIMAL(10,2) DEFAULT 0.00,
    submitted_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (unit_id)   REFERENCES units(unit_id),
    FOREIGN KEY (tenant_id) REFERENCES users(user_id)
);

-- Seed data
INSERT INTO malls (name, address, city) VALUES
    ('Northgate Mall',  '100 North Ave',  'Toronto'),
    ('Southview Plaza', '200 South Blvd', 'Mississauga');

INSERT INTO users (full_name, email, password_hash, role) VALUES
    ('Admin User',   'admin@rems.com',  SHA2('admin123', 256),  'ADMIN'),
    ('Agent Smith',  'agent@rems.com',  SHA2('agent123', 256),  'AGENT'),
    ('Tenant Jones', 'tenant@rems.com', SHA2('tenant123', 256), 'TENANT');

INSERT INTO units (mall_id, unit_number, size_sqft, base_rate, tier, usage_type, status) VALUES
    (1, 'A-101', 500.00,  2500.00, 1, 'Retail',     'AVAILABLE'),
    (1, 'A-102', 800.00,  3200.00, 2, 'Food & Bev', 'AVAILABLE'),
    (1, 'B-201', 1200.00, 4800.00, 3, 'Anchor',     'AVAILABLE'),
    (2, 'C-101', 400.00,  1800.00, 1, 'Retail',     'AVAILABLE'),
    (2, 'C-202', 950.00,  3900.00, 4, 'Luxury',     'LEASED');
