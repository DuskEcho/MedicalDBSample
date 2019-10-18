-- Greg Brown
-- SAGA
-- The following view is intended to give an overview
-- Of recent visits and conditions at the clinic.
-- This gives the viewer an idea of frequency, type, 
-- and identifications of current events and can be used for both
-- pattern exploration and decisionmaking (when to staff, common problems)

CREATE VIEW RecentActivity as
(SELECT LastName, FirstName, ConditionDescription, VisitDate
FROM visit v
JOIN patient pa ON v.PatientID = pa.PatientId
JOIN conditions c ON v.PatientID = c.patientID
WHERE c.CurrentlyActive = true
ORDER BY VisitDate DESC);
SELECT * FROM RecentActivity;
