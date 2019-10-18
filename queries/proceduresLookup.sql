-- Greg Brown
-- SAGA
-- The following query is used to display a/many procedure(s) for a patient
-- Patient name, description, date, and physician are shown as part 
-- of the summary
--
--  In the servlet, the last two line is in form:
-- "WHERE FirstName LIKE ? AND LastName LIKE ? AND ProcedureID LIKE ?"
-- Actual values are given here for quick-testing

SELECT FirstName, LastName, ProcedureDescription, ProcedureDate, ph.PhysicianName as PhysicianName 
FROM patient 
NATURAL JOIN procedures p 
JOIN physicians ph  ON p.PhysicianID = ph.PhysicianID
WHERE FirstName LIKE "Frank" AND LastName LIKE "Redbeard" AND ProcedureID LIKE "2"
