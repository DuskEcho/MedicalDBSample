/* Dalia Faria
SAGA (Team 2)
Query used in SearchProfileServlet for PatientProfile.html webpage.
Values have been included in the query below
for testing purposes in MySQL Workbench.

In the servlet, the query defaults to "%" values if fields are left empty.
*/

SELECT * FROM patient
WHERE FirstName LIKE "Pamela" AND LastName Like "%" AND PatientID LIKE "%" AND Bday LIKE "1959" AND Phone LIKE "%";
