-- Greg Brown
-- SAGA
-- The following query is used to schedule a visit for a patient
-- Note that "false" is always used for column "completed"
-- as it should not be possible to "schedule" a visit that 
-- has already been completed.
--
--  In the servlet, the last two line is in form:
-- "(SELECT PatientID FROM patient WHERE FirstName = ? AND LastName = ?), ?, ?, ?, false);"
-- Actual values are given here for quick-testing

INSERT INTO visit (PatientID, VisitDescription, VisitDate, PhysicianID, Completed) VALUES (
(SELECT PatientID FROM patient WHERE FirstName = "Frank" AND LastName = "Redbeard"), "wobbly peg", "2019-10-05", 1, false);
