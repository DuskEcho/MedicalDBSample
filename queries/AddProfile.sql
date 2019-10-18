/* Dalia Faria
SAGA (Team 2)
Query used in AddProfileServlet for PatientProfile.html webpage.
Values have been included in the query below, including a SELECT
statement for testing purposes in MySQL Workbench.
*/

INSERT INTO patient(FirstName, LastName, Bday, Phone, EmergencyFirstName,
 EmergencyLastName, Weight, Height, BloodType)
 values ("Rosie", "Posie", "2013", "192-485-9387", "Dalia", "Faria", "5", "4", "A+");
 
 SELECT * FROM patient;