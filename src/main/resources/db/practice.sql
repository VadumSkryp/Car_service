-- ==========================================
--  practice.sql
-- ==========================================

USE car_service;

-- ==========================================
-- 1. LEFT JOINS (10 штук)
-- ==========================================

-- 1
SELECT c.name, car.license_plate
FROM Customer c
LEFT JOIN Car car ON c.id = car.customer_id;

-- 2
SELECT car.license_plate, cm.manufacturer, cm.model_name
FROM Car car
LEFT JOIN CarModel cm ON car.car_model_id = cm.id;

-- 3
SELECT so.id AS order_id, so.status, m.name AS mechanic_name
FROM ServiceOrder so
LEFT JOIN Mechanic m ON so.mechanic_id = m.id;

-- 4
SELECT so.id, st.description, sot.quantity
FROM ServiceOrder so
LEFT JOIN ServiceOrder_Task sot ON so.id = sot.service_order_id
LEFT JOIN ServiceTask st ON sot.service_task_id = st.id;

-- 5
SELECT so.id, p.name AS part_name, sop.quantity
FROM ServiceOrder so
LEFT JOIN ServiceOrder_Part sop ON so.id = sop.service_order_id
LEFT JOIN Part p ON sop.part_id = p.id;

-- 6
SELECT inv.id, inv.total_amount, pay.amount
FROM Invoice inv
LEFT JOIN Payment pay ON inv.id = pay.invoice_id;

-- 7
SELECT m.name, s.skill_name
FROM Mechanic m
LEFT JOIN Mechanic_Skill ms ON m.id = ms.mechanic_id
LEFT JOIN Skill s ON ms.skill_id = s.id;

-- 8
SELECT c.name, cod.address, cod.birth_date
FROM Customer c
LEFT JOIN CarOwnerDetails cod ON c.id = cod.customer_id;

-- 9
SELECT so.id, rte.estimated_hours, rte.actual_hours
FROM ServiceOrder so
LEFT JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id;

-- 10 (з AVG)
SELECT c.name, AVG(rte.actual_hours) AS avg_hours
FROM Customer c
LEFT JOIN Car car ON c.id = car.customer_id
LEFT JOIN ServiceOrder so ON car.id = so.car_id
LEFT JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id
GROUP BY c.name;

-- ==========================================
-- 2. RIGHT JOINS (10 штук)
-- ==========================================

-- 1
SELECT c.name, car.license_plate
FROM Customer c
RIGHT JOIN Car car ON c.id = car.customer_id;

-- 2
SELECT car.license_plate, cm.manufacturer
FROM Car car
RIGHT JOIN CarModel cm ON car.car_model_id = cm.id;

-- 3
SELECT so.id, m.name
FROM ServiceOrder so
RIGHT JOIN Mechanic m ON so.mechanic_id = m.id;

-- 4
SELECT st.description, sot.quantity
FROM ServiceTask st
RIGHT JOIN ServiceOrder_Task sot ON st.id = sot.service_task_id;

-- 5
SELECT p.name, sop.quantity
FROM Part p
RIGHT JOIN ServiceOrder_Part sop ON p.id = sop.part_id;

-- 6
SELECT inv.total_amount, pay.amount
FROM Invoice inv
RIGHT JOIN Payment pay ON inv.id = pay.invoice_id;

-- 7
SELECT s.skill_name, m.name
FROM Skill s
RIGHT JOIN Mechanic_Skill ms ON s.id = ms.skill_id
RIGHT JOIN Mechanic m ON ms.mechanic_id = m.id;

-- 8
SELECT cod.address, c.name
FROM CarOwnerDetails cod
RIGHT JOIN Customer c ON cod.customer_id = c.id;

-- 9
SELECT rte.estimated_hours, so.status
FROM RepairTimeEstimate rte
RIGHT JOIN ServiceOrder so ON rte.service_order_id = so.id;

-- 10 (з SUM)
SELECT s.skill_name, SUM(inv.total_amount) AS total_income
FROM Skill s
RIGHT JOIN Mechanic_Skill ms ON s.id = ms.skill_id
RIGHT JOIN Mechanic m ON ms.mechanic_id = m.id
RIGHT JOIN ServiceOrder so ON m.id = so.mechanic_id
RIGHT JOIN Invoice inv ON so.id = inv.service_order_id
GROUP BY s.skill_name;

-- ==========================================
-- 3. INNER JOINS (10 штук)
-- ==========================================

-- 1
SELECT c.name, car.license_plate
FROM Customer c
INNER JOIN Car car ON c.id = car.customer_id;

-- 2
SELECT car.license_plate, cm.manufacturer
FROM Car car
INNER JOIN CarModel cm ON car.car_model_id = cm.id;

-- 3
SELECT so.id, m.name
FROM ServiceOrder so
INNER JOIN Mechanic m ON so.mechanic_id = m.id;

-- 4
SELECT so.id, st.description
FROM ServiceOrder so
INNER JOIN ServiceOrder_Task sot ON so.id = sot.service_order_id
INNER JOIN ServiceTask st ON sot.service_task_id = st.id;

-- 5
SELECT so.id, p.name
FROM ServiceOrder so
INNER JOIN ServiceOrder_Part sop ON so.id = sop.service_order_id
INNER JOIN Part p ON sop.part_id = p.id;

-- 6
SELECT inv.id, pay.amount
FROM Invoice inv
INNER JOIN Payment pay ON inv.id = pay.invoice_id;

-- 7
SELECT m.name, s.skill_name
FROM Mechanic m
INNER JOIN Mechanic_Skill ms ON m.id = ms.mechanic_id
INNER JOIN Skill s ON ms.skill_id = s.id;

-- 8
SELECT c.name, cod.address
FROM Customer c
INNER JOIN CarOwnerDetails cod ON c.id = cod.customer_id;

-- 9
SELECT so.id, rte.estimated_hours
FROM ServiceOrder so
INNER JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id;

-- 10 (з MAX)
SELECT m.name, MAX(rte.actual_hours) AS max_hours
FROM Mechanic m
INNER JOIN ServiceOrder so ON m.id = so.mechanic_id
INNER JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id
GROUP BY m.name;

-- ==========================================
-- 4. FULL OUTER JOINS (10 штук) через UNION
-- ==========================================

-- 1
SELECT c.name, car.license_plate
FROM Customer c
LEFT JOIN Car car ON c.id = car.customer_id
UNION
SELECT c.name, car.license_plate
FROM Customer c
RIGHT JOIN Car car ON c.id = car.customer_id;

-- 2
SELECT car.license_plate, cm.manufacturer
FROM Car car
LEFT JOIN CarModel cm ON car.car_model_id = cm.id
UNION
SELECT car.license_plate, cm.manufacturer
FROM Car car
RIGHT JOIN CarModel cm ON car.car_model_id = cm.id;

-- 3
SELECT so.id, m.name
FROM ServiceOrder so
LEFT JOIN Mechanic m ON so.mechanic_id = m.id
UNION
SELECT so.id, m.name
FROM ServiceOrder so
RIGHT JOIN Mechanic m ON so.mechanic_id = m.id;

-- 4
SELECT st.description, sot.quantity
FROM ServiceTask st
LEFT JOIN ServiceOrder_Task sot ON st.id = sot.service_task_id
UNION
SELECT st.description, sot.quantity
FROM ServiceTask st
RIGHT JOIN ServiceOrder_Task sot ON st.id = sot.service_task_id;

-- 5
SELECT p.name, sop.quantity
FROM Part p
LEFT JOIN ServiceOrder_Part sop ON p.id = sop.part_id
UNION
SELECT p.name, sop.quantity
FROM Part p
RIGHT JOIN ServiceOrder_Part sop ON p.id = sop.part_id;

-- 6
SELECT inv.total_amount, pay.amount
FROM Invoice inv
LEFT JOIN Payment pay ON inv.id = pay.invoice_id
UNION
SELECT inv.total_amount, pay.amount
FROM Invoice inv
RIGHT JOIN Payment pay ON inv.id = pay.invoice_id;

-- 7
SELECT s.skill_name, m.name
FROM Skill s
LEFT JOIN Mechanic_Skill ms ON s.id = ms.skill_id
LEFT JOIN Mechanic m ON ms.mechanic_id = m.id
UNION
SELECT s.skill_name, m.name
FROM Skill s
RIGHT JOIN Mechanic_Skill ms ON s.id = ms.skill_id
RIGHT JOIN Mechanic m ON ms.mechanic_id = m.id;

-- 8
SELECT c.name, cod.address
FROM Customer c
LEFT JOIN CarOwnerDetails cod ON c.id = cod.customer_id
UNION
SELECT c.name, cod.address
FROM Customer c
RIGHT JOIN CarOwnerDetails cod ON c.id = cod.customer_id;

-- 9
SELECT so.id, rte.estimated_hours
FROM ServiceOrder so
LEFT JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id
UNION
SELECT so.id, rte.estimated_hours
FROM ServiceOrder so
RIGHT JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id;

-- 10 (з MIN)
SELECT m.name, MIN(rte.actual_hours) AS min_hours
FROM Mechanic m
LEFT JOIN ServiceOrder so ON m.id = so.mechanic_id
LEFT JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id
GROUP BY m.name
UNION
SELECT m.name, MIN(rte.actual_hours) AS min_hours
FROM Mechanic m
RIGHT JOIN ServiceOrder so ON m.id = so.mechanic_id
RIGHT JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id
GROUP BY m.name;

-- ==========================================
-- 5. BIG QUERY JOIN ALL TABLES
-- ==========================================
SELECT c.name AS customer_name, cm.manufacturer, cm.model_name, car.license_plate,
       so.status, m.name AS mechanic_name, st.description, p.name AS part_name,
       inv.total_amount, pay.amount AS payment_amount,
       s.skill_name, cod.address, rte.estimated_hours, rte.actual_hours
FROM Customer c
JOIN Car car ON c.id = car.customer_id
JOIN CarModel cm ON car.car_model_id = cm.id
JOIN ServiceOrder so ON car.id = so.car_id
JOIN Mechanic m ON so.mechanic_id = m.id
JOIN Mechanic_Skill ms ON m.id = ms.mechanic_id
JOIN Skill s ON ms.skill_id = s.id
JOIN ServiceOrder_Task sot ON so.id = sot.service_order_id
JOIN ServiceTask st ON sot.service_task_id = st.id
JOIN ServiceOrder_Part sop ON so.id = sop.service_order_id
JOIN Part p ON sop.part_id = p.id
JOIN Invoice inv ON so.id = inv.service_order_id
JOIN Payment pay ON inv.id = pay.invoice_id
JOIN CarOwnerDetails cod ON c.id = cod.customer_id
JOIN RepairTimeEstimate rte ON so.id = rte.service_order_id;

-- ==========================================
-- 6. GROUP BY (3 шт)
-- ==========================================

-- 1
SELECT m.name, COUNT(so.id) AS total_orders
FROM Mechanic m
JOIN ServiceOrder so ON m.id = so.mechanic_id
GROUP BY m.name;

-- 2
SELECT c.name, SUM(inv.total_amount) AS total_spent
FROM Customer c
JOIN Car car ON c.id = car.customer_id
JOIN ServiceOrder so ON car.id = so.car_id
JOIN Invoice inv ON so.id = inv.service_order_id
GROUP BY c.name;

-- 3
SELECT s.skill_name, AVG(inv.total_amount) AS avg_order_price
FROM Skill s
JOIN Mechanic_Skill ms ON s.id = ms.skill_id
JOIN Mechanic m ON ms.mechanic_id = m.id
JOIN ServiceOrder so ON m.id = so.mechanic_id
JOIN Invoice inv ON so.id = inv.service_order_id
GROUP BY s.skill_name;

-- ==========================================
-- 7. HAVING (3 шт)
-- ==========================================

-- 1
SELECT m.name, COUNT(so.id) AS total_orders
FROM Mechanic m
JOIN ServiceOrder so ON m.id = so.mechanic_id
GROUP BY m.name
HAVING COUNT(so.id) > 1;

-- 2
SELECT c.name, SUM(inv.total_amount) AS total_spent
FROM Customer c
JOIN Car car ON c.id = car.customer_id
JOIN ServiceOrder so ON car.id = so.car_id
JOIN Invoice inv ON so.id = inv.service_order_id
GROUP BY c.name
HAVING SUM(inv.total_amount) > 800;

-- 3
SELECT s.skill_name, AVG(inv.total_amount) AS avg_price
FROM Skill s
JOIN Mechanic_Skill ms ON s.id = ms.skill_id
JOIN Mechanic m ON ms.mechanic_id = m.id
JOIN ServiceOrder so ON m.id = so.mechanic_id
JOIN Invoice inv ON so.id = inv.service_order_id
GROUP BY s.skill_name
HAVING AVG(inv.total_amount) > 600;
