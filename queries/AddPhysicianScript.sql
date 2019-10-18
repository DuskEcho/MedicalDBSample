#Andrew Terrado, SAGA TEAM 2
#This script will add a physician entry into the physicians table
#The physicianID is auto generated and not needed
#In this example, Luigi is added.
INSERT INTO physicians (PhysicianName, ContactNumber)
VALUES ("Luigi", "555-555-5556");


#This script will return a verification (for testing)
SELECT * FROM physicians 
WHERE PhysicianName = "Luigi"
