-- The following query is used to look up a patient's visits by most "recent".
-- In the case of appointments, appointments are displayed from
-- furthest out to closest. 
--
--  In the servlet, the last two lines are in form:
-- "WHERE FirstName LIKE ? AND LastName LIKE ? AND
-- v.Completed = ? ORDER BY VisitDate DESC;"
-- Actual values are given here for quick-testing


SELECT FirstName, LastName, ph.PhysicianName as PhysicianName, VisitDescription, VisitDate
FROM Patient p
JOIN visit v ON p.PatientId = v.PatientId
JOIN physicians ph on ph.`PhysicianID` = v.`PhysicianID`
WHERE FirstName LIKE ? AND LastName LIKE ? AND
v.Completed = ? ORDER BY VisitDate DESC;
