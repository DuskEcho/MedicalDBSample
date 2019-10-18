#Andrew Terrado, SAGA TEAM 2
#This script will list all patient vists by a specified physician 
#It will also list the date and patient seen on the visit
SELECT PhysicianName as PhysicianName, FirstName, LastName, VisitDate, v.physicianID
FROM physicians
NATURAL JOIN visit v Join patient  ON v.PhysicianID = physicians.PhysicianID 
WHERE v.PhysicianID = 1 and PhysicianName = "Stephen Strange"
ORDER BY PhysicianName, visitDate
