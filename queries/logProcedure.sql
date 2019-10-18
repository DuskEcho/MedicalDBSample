-- Greg Brown
-- SAGA
-- The following query is used to log a procedure performed
-- Note that it is the user's responsibility to enter valid Condition and PatientIDs.
-- This is assumed to be performed in an environment where both are readily available, e.g.,
-- At the clinic just after a procedure with PatientID and ConditionID physically present in some form
-- (ID card, condition info "pulled up" on the monitor)
--
--  In the servlet, the last line is in form:
-- "(SELECT PatientID FROM patient WHERE PatientID = ?), ?, ?, ?, ?, ?"
-- Actual values are given here for quick-testing

INSERT INTO procedures (PatientID, ProcedureDescription, ProcedureDate, ConditionID, Result, PhysicianID)
VALUES (
(SELECT PatientID FROM patient WHERE PatientID = "1"), "New Peg", "2012-01-01", 1, "Ready to sail", 1);
