--  The following view is intended to give an overview of recent occurrences. 
--  Patients, visits, and associated conditions are displayed in descending order
--  by date

CREATE VIEW RecentActivity as
(SELECT LastName, FirstName, ConditionDescription, VisitDate
FROM visit v
JOIN patient pa ON v.PatientID = pa.PatientId
JOIN conditions c ON v.PatientID = c.patientID
WHERE c.CurrentlyActive = true
ORDER BY VisitDate DESC);
