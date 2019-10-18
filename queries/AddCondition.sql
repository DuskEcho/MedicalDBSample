/* Dalia Faria
SAGA (Team 2)
Query used in AddConditionServlet for PatientCondition.html webpage.
Values and a SELECT statement have been included in the query below
for testing purposes in MySQL Workbench.

*/

INSERT INTO conditions (PatientID, ConditionDescription, CurrentlyActive, TreatmentPlan, DiagnosedVisitID)
values("9", "confused 24/7", "1", "suggested therapy", "10");

SELECT * FROM conditions;
