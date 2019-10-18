/* Dalia Faria
SAGA (Team 2)
Query used in SearchProfileServlet for PatientProfile.html webpage.
Values have been included in the query below
for testing purposes in MySQL Workbench.

In the servlet, the query defaults to "%" values if fields are left empty.
*/

SELECT p.PatientID, FirstName, LastName, ConditionID, ConditionDescription,
CurrentlyActive, TreatmentPlan, DiagnosedVisitID
FROM patient p JOIN conditions c ON p.PatientID = c.PatientID
WHERE FirstName LIKE "Bruce" AND LastName LIKE "%" AND p.PatientID LIKE "%";