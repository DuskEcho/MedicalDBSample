#Andrew Terrado, SAGA TEAM 2
#This script will provide all of the physician information based on partial physician data.
#The ID or name will return the complete physician entry
SELECT * FROM physicians 
WHERE PhysicianID =2 AND PhysicianName = "Mario"