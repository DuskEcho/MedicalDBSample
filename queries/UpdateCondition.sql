/* Dalia Faria
SAGA (Team 2)
Query used in UpdateConditionServlet for PatientCondition.html webpage.
Values and a SELECT statement have been included in the query below
for testing purposes in MySQL Workbench.

In the servlet, the query defaults to "%" values if the name fields are left empty.
*/

UPDATE conditions SET currentlyActive = "0" WHERE ConditionID = "6" AND PatientID = "6";

SELECT * FROM conditions;