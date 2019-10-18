/*
 * Dalia Faria
 * SAGA (Team 2)
 * 
 * This program interacts with the "Log New Condition for Patient" form. 
 * Upon submission, the values will update
 * into the medical database, successfully creating a new patient.
 * 
 * Precondition: All fields are required. PatientCondition.html
 * and database schema must be set up prior to
 * program run. Server credentials must be set to "user" and 
 * password must be set to local host's password or "sesame80" as below.
 * Jdk version must be set to your current jdk version.
 * 
 * Postcondition: The input values will store into the
 * medical database. Successful condition logged for a patient.
 * 
 */


package MedicalDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Core servlet implementation
 */
@WebServlet("/AddConditionServlet")
public class AddConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";
	
	// SQL statements
	String sql = "INSERT INTO conditions (PatientID, ConditionDescription, CurrentlyActive, TreatmentPlan, DiagnosedVisitID)\r\n" + 
			"values(?, ?, 1, ?, ?);";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String patientID = request.getParameter("patientID");
		String conditionDescription = request.getParameter("conditionDescription");
		String treatmentPlan = request.getParameter("treatmentPlan");
		String diagnosedVisitID = request.getParameter("diagnosedVisitID");
	
		
		response.setContentType("text/html");   
		PrintWriter out = response.getWriter();

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			conn = DriverManager.getConnection(
                  DB_URL, USER, PASS);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);; // serializeable
			conn.setAutoCommit(false);


			// now prepare and execute update or insert 
			// sql statement
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, patientID);
			pstmt.setString(2, conditionDescription);
			pstmt.setString(3, treatmentPlan);
			pstmt.setString(4, diagnosedVisitID);
		
			int nrows = pstmt.executeUpdate();
			conn.commit();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<p>" + nrows + " Rows Updated. Patient Condition Information Saved.</p>");
			out.println("<a href=\"PatientProcedures.html\">Click Here to record Procedure.</a>");
			
			pstmt.close();
			conn.close();
			out.flush();
		} catch (Exception e) {
			// Handle errors
			e.printStackTrace();
		} // end try
	}
}