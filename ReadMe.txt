MedicalDB Final Group Project
Greg Brown, Andrew Terrado, Dalia Faria


Instructions to set up: 
1. Run SchemaSetup.sql in MySql Workbench. “User” host and default table values will create upon initial run.
2. Import MedicalDBSample.war in Eclipse by right-clicking anywhere in the Project Explorer (inspector) and clicking on Import... ->Web->WAR file.
3. Expand the WebContent folder and locate “Home.html”. Right-click the file and click on “Run as…”. You should have a configuration option to run your local server.


To Know:
Each patient can have several conditions and procedures done in a single visit.
A patient can also have several visits. It is possible for the conditions and procedures to be performed on different days than the initial scheduled visit date, but is not usual unless there is first a scheduled follow up visit. 


  

Maneuvering the Home Page:
The user will have several options here, Patient Profile, Patient Visits, Patient Conditions, Patient Procedures, and Physicians. Recent Occurrences. The user is suggested to visit the webpages in this order.
  
  



Patient Profile: There are 2 forms on this webpage, “Log New Patient” and “Search for Patient”.


Log New Patient - All fields, except the emergency contact fields, are required. There are prompts in the description to help the user input appropriate values for the fields such as birth year format, weight, and height. Once the user has clicked on submit, there will either be a notification that the rows have been updated and new patient information was saved, or prompt to check user input values. If successful, the user is prompted to make an appointment which will redirect them to Patient Visits.


Search for Patient - This is meant for searching existing patients only. There are prompts in the description to help the user input appropriate values for the fields such as birth year and phone number. No values are necessary for input in order to perform a search. Once the user has clicked on submit, patient results will display based on the user input values or prompt to check user input values. The results will include a patient’s first and last name, patient ID, birth year, phone number, emergency first and last name, weight, height, and blood type. More user input values present in the fields will return a refined search.
  
  
  



Patient Visits: There are 2 forms on this webpage, “Look Up Visits” and “Schedule New Visit”.


Look Up Visits - No input is required to run this search. The result will display an overview of all completed visits since the drop-down option is default marked to “Yes”. The user can simply change this to “No” in order to produce a result of the incomplete visits. More user input values present in the fields will return a refined search. The results will include a patient’s first and last name, physician, visit summary, visit ID, and visit date.


Schedule New Visit - This form is meant for existing patients only. All fields are required. There is a prompt in the description that redirect new patients to the Patient Profile webpage if they had not already made a profile, and a prompt to help the user input an appropriate value for the visit date field. If the user tries to schedule a visit without existing in the database or leaving fields blank, a prompt to check user input values will display. Once the user has input all fields and clicked on submit, the user is prompted to add conditions which will redirect them to Patient Conditions.






  
       

Patient Conditions: There are 4 forms on this webpage, “Log New Condition for Patient”, “Update Condition/Completion Status for Patient”, “Search Patient Condition History”, and “Find Patient Based on Condition”.


Log New Condition for Patient - This form is meant for existing patients only. All fields are required. Completing this form assumes that the condition is currently active. If fields are missing, the user will be prompted to check user input values. The Diagnosed Visit ID refers to the primary key Visit ID that is generated upon making a new appointment. You can refer back to the “Look Up Visits” form on the Patient Visits webpage for this value. Once the user has clicked on submit, there will be a notification that the rows have been updated and patient condition information was saved. The user is then prompted to record procedure which will redirect them to Patient Procedure.


Update Condition/Completion Status for Patient - This form is meant for physician use after a patient procedure has been committed. All fields are required to update a condition/completion status for a patient. The physician is prompted for their Patient ID to prevent discrepancies between identically named patients. Patient ID can be found by referring back to “Search for Patient” in the Patient Profile webpage. ConditionID can be found by referring to “Search Patient Condition History” further down this page. Visit ID can be found under incomplete visits in the  “Look Up Visits” form on the Patient Visits webpage. Currently Active refers to the patient’s condition whereas Completion Status refers to a completed visit . For example, a patient that came in with an earache was prescribed ear drops and went home. The physician would then mark their condition as not currently active, and the completion status as complete for this single condition.

Search Patient Condition History -  No input is required to run this search. The result will display an overview of all patients’ condition history. More user input values present in the fields will return a refined search. The results will include the patient ID, patient’s first and last name, condition ID, condition description, currently active status, treatment plan, and diagnosed visit ID, which is a reference to a patient’s visit ID.


FInd Patient Based on Condition - No input is required to run this search. The result will display an overview of all patients sharing a particular condition. More user input values present in the fields will return a refined search. The results will include the patient’s first and last name, patient ID, condition description, condition ID, and currently active status.


  
  



Patient Procedures: There are 2 forms on this webpage, “Log New Procedure” and “Look Up Procedure”.


Log New Procedure - This form is meant to allow physicians to report what kind of procedures, such as supplied medications, surgeries, references, etc., were done in addition to the treatment plan reported in the”Log New Condition for Patient” on the Patient Condition webpage. All fields are required. If fields are missing, the user will be prompted to check user input values. Once the physician has clicked on submit, there will be a notification that the rows have been updated and is then prompted to update condition/completion status which will redirect them to Patient Conditions.


Look Up Procedure - No input is required to run this search. The result will display an overview of all procedures performed. More user input values present in the fields will return a refined search. The results will include the patient’s first and last name, procedure description, procedure date, and physician.


  

  
  





Physicians: There are 3 forms on this webpage, “Look Up Physician Account Information”, “Physician Visit Log”, and “Add New Physician”.


Look Up Physician Account Information - Returns physician information based on user inputted fragments of physician data (ID, name, and/or contact number). IE: If you enter only the physician ID, it will return the physician entry tied to that profile. If no input is given, a complete directory of physicians is returned.


Physician Visit Log -  Allows users to input a physician ID and/or physician name. With this information, the servlet will return a complete list of the physician's visit history, including visit date and patient.


Add New Physician - Allows users to add in a new physician into the physician data table. It requires the user to input at least a name. The physician ID is auto incremented and does not require user input. 


  



Recent Occurrences: This form is found on the Home webpage. There are no user input fields required. The user can simply click on Recent Occurrences to see a view of all the most recent conditions. The results will include the patient’s last name, first name, condition, and visit date.