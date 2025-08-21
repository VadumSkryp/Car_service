-- Create database
CREATE DATABASE car_service;
USE car_service;

-- 1. Customer
CREATE TABLE Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100)
);

-- 2. CarModel
CREATE TABLE CarModel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    manufacturer VARCHAR(100) NOT NULL,
    model_name VARCHAR(100) NOT NULL
);

-- 3. Car
CREATE TABLE Car (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    car_model_id INT NOT NULL,
    license_plate VARCHAR(20) UNIQUE,
    year YEAR,
    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (car_model_id) REFERENCES CarModel(id)
);

-- 4. Mechanic
CREATE TABLE Mechanic (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20)
);

-- 5. ServiceOrder
CREATE TABLE ServiceOrder (
    id INT PRIMARY KEY AUTO_INCREMENT,
    car_id INT NOT NULL,
    mechanic_id INT NOT NULL,
    order_date DATE,
    status ENUM('In Progress','Completed','Pending') DEFAULT 'In Progress',
    FOREIGN KEY (car_id) REFERENCES Car(id),
    FOREIGN KEY (mechanic_id) REFERENCES Mechanic(id)
);

-- 6. ServiceTask
CREATE TABLE ServiceTask (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    standard_price DECIMAL(10,2)
);

-- 7. Part
CREATE TABLE Part (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2),
    stock_quantity INT
);

-- 8. ServiceOrder_Task
CREATE TABLE ServiceOrder_Task (
    service_order_id INT,
    service_task_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (service_order_id, service_task_id),
    FOREIGN KEY (service_order_id) REFERENCES ServiceOrder(id),
    FOREIGN KEY (service_task_id) REFERENCES ServiceTask(id)
);

-- 9. ServiceOrder_Part
CREATE TABLE ServiceOrder_Part (
    service_order_id INT,
    part_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (service_order_id, part_id),
    FOREIGN KEY (service_order_id) REFERENCES ServiceOrder(id),
    FOREIGN KEY (part_id) REFERENCES Part(id)
);

-- 10. Invoice
CREATE TABLE Invoice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    service_order_id INT UNIQUE,
    total_amount DECIMAL(10,2),
    invoice_date DATE,
    FOREIGN KEY (service_order_id) REFERENCES ServiceOrder(id)
);

-- 11. Payment
CREATE TABLE Payment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    invoice_id INT NOT NULL,
    payment_date DATE,
    amount DECIMAL(10,2),
    FOREIGN KEY (invoice_id) REFERENCES Invoice(id)
);

-- 12. Skill
CREATE TABLE Skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(100) NOT NULL
);

-- 13. Mechanic_Skill
CREATE TABLE Mechanic_Skill (
    mechanic_id INT,
    skill_id INT,
    PRIMARY KEY (mechanic_id, skill_id),
    FOREIGN KEY (mechanic_id) REFERENCES Mechanic(id),
    FOREIGN KEY (skill_id) REFERENCES Skill(id)
);

-- 14. CarOwnerDetails
CREATE TABLE CarOwnerDetails (
    customer_id INT PRIMARY KEY,
    address VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- 15. RepairTimeEstimate
CREATE TABLE RepairTimeEstimate (
    service_order_id INT PRIMARY KEY,
    estimated_hours DECIMAL(5,2),
    actual_hours DECIMAL(5,2),
    FOREIGN KEY (service_order_id) REFERENCES ServiceOrder(id)
);

-- --------------------------------------------------------
-- INSERT DATA
-- --------------------------------------------------------

-- Customers
INSERT INTO Customer (name, phone, email) VALUES
('John Smith', '15551234567', 'john.smith@example.com'),
('Emma Johnson', '15559876543', 'emma.johnson@example.com'),
('Michael Brown', '15553456789', 'michael.brown@example.com'),
('Olivia Davis', '15555678901', 'olivia.davis@example.com'),
('William Miller', '15552345678', 'william.miller@example.com');

-- CarOwnerDetails
INSERT INTO CarOwnerDetails (customer_id, address, birth_date) VALUES
(1, 'New York, 5th Avenue, 101', '1985-04-12'),
(2, 'Los Angeles, Sunset Blvd, 250', '1990-07-20'),
(3, 'Chicago, Michigan Ave, 55', '1978-12-05'),
(4, 'Miami, Ocean Drive, 12', '1995-03-15'),
(5, 'Dallas, Main Street, 88', '1982-11-09');

-- Car Models
INSERT INTO CarModel (manufacturer, model_name) VALUES
('Toyota', 'Corolla'),
('BMW', 'X5'),
('Volkswagen', 'Passat'),
('Ford', 'Focus'),
('Honda', 'Civic');

-- Cars
INSERT INTO Car (customer_id, car_model_id, license_plate, year) VALUES
(1, 1, 'NY1234AA', 2015),
(2, 2, 'CA5678BB', 2019),
(3, 3, 'IL4321CC', 2012),
(4, 4, 'FL8765DD', 2021),
(5, 5, 'TX9999EE', 2018);

-- Mechanics
INSERT INTO Mechanic (name, phone) VALUES
('Robert Wilson', '15554321111'),
('James Anderson', '15557654321'),
('Daniel Thomas', '15551239876');

-- Skills
INSERT INTO Skill (skill_name) VALUES
('Engine'),
('Electrical'),
('Bodywork'),
('Transmission');

-- Mechanic Skills
INSERT INTO Mechanic_Skill (mechanic_id, skill_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(2, 1);

-- Service Orders
INSERT INTO ServiceOrder (car_id, mechanic_id, order_date, status) VALUES
(1, 1, '2025-08-01', 'In Progress'),
(2, 2, '2025-08-05', 'Completed'),
(3, 1, '2025-08-07', 'Pending'),
(4, 3, '2025-08-08', 'In Progress'),
(5, 2, '2025-08-10', 'Completed');

-- Service Tasks
INSERT INTO ServiceTask (description, standard_price) VALUES
('Oil change', 100.00),
('Engine repair', 500.00),
('Body painting', 700.00),
('Brake replacement', 250.00),
('Transmission repair', 800.00);

-- Parts
INSERT INTO Part (name, price, stock_quantity) VALUES
('Oil filter', 30.00, 10),
('Motor oil 5W-30', 40.00, 20),
('Front headlight', 150.00, 5),
('Brake pads', 60.00, 15),
('Gearbox kit', 500.00, 2);

-- ServiceOrder_Tasks
INSERT INTO ServiceOrder_Task (service_order_id, service_task_id, quantity) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 1),
(3, 4, 2),
(4, 5, 1),
(5, 1, 1),
(5, 4, 1);

-- ServiceOrder_Parts
INSERT INTO ServiceOrder_Part (service_order_id, part_id, quantity) VALUES
(1, 1, 1),
(1, 2, 4),
(2, 3, 2),
(3, 4, 4),
(4, 5, 1),
(5, 2, 2);

-- Invoices
INSERT INTO Invoice (service_order_id, total_amount, invoice_date) VALUES
(1, 650.00, '2025-08-02'),
(2, 1200.00, '2025-08-06'),
(3, 350.00, '2025-08-08'),
(4, 900.00, '2025-08-09'),
(5, 500.00, '2025-08-11');

-- Payments
INSERT INTO Payment (invoice_id, payment_date, amount) VALUES
(1, '2025-08-03', 650.00),
(2, '2025-08-07', 1200.00),
(4, '2025-08-09', 900.00),
(5, '2025-08-11', 500.00);

-- Repair Time Estimates
INSERT INTO RepairTimeEstimate (service_order_id, estimated_hours, actual_hours) VALUES
(1, 8.00, 9.50),
(2, 12.00, 11.00),
(3, 4.00, 5.00),
(4, 10.00, 10.50),
(5, 6.00, 6.50);
