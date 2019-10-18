/* Dalia Faria
SAGA (Team 2)
Query used in FindOnConditionServlet for PatientCondition.html webpage.
Values have been included in the query below
for testing purposes in MySQL Workbench.

In the servlet, the query defaults to "%" values if fields are left empty.
*/

SELECT FirstName, LastName, c.PatientID, ConditionDescription, ConditionID, CurrentlyActive, TreatmentPlan, DiagnosedVisitID 
FROM conditions c JOIN patient p ON c.PatientID = p.PatientID
WHERE ConditionDescription LIKE "%leg%" AND ConditionID LIKE "%"
ORDER BY CurrentlyActive;

SELECT * FROM conditions;