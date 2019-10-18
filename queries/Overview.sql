-- Greg Brown
-- SAGA
-- The following view is intended to give an overview
-- Of recent visits and conditions at the clinic.
-- This gives the viewer an idea of frequency, type, 
-- and identifications of current events and can be used for both
-- pattern exploration and decisionmaking (when to staff, common problems)

CREATE OR REPLACE VIEW RecentActivity as
            (SELECT FirstName, LastName, ConditionDescription, VisitDate, CurrentlyActive
            FROM patient p JOIN conditions c ON p.PatientID = c.PatientID JOIN visit v
            ON c.DiagnosedVisitID = v.VisitID
            ORDER BY VisitDate DESC);
