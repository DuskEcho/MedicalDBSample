#Andrew Terrado, SAGA TEAM 2
#This script will list all patient vists by a specified physician 
#It will also list the date and patient seen on the visit

SELECT phy.PhysicianID, phy.PhysicianName, p.FirstName, p.LastName, VisitDate
FROM physicians phy JOIN visit v ON phy.PhysicianID = v.physicianID
JOIN patient p ON v.PatientID = p.PatientID
WHERE phy.PhysicianID LIKE "%" AND PhysicianName LIKE "%Meredith%"
ORDER BY VisitDate DESC;