/* 
 * Dalia Faria
 * SAGA (Team 2)
 * 
 * This program interacts with the "Update Condition Status for Patient" form. 
 * Upon submission, the values will update
 * into the medical database, successfully updating the "currently active" column.
 * 
 * Precondition: All fields are required. PatientCondition.html
 * and database schema must be set up prior to
 * program run. Server credentials must be set to "user" and 
 * password must be set to local host's password or "sesame80" as below.
 * Jdk version must be set to your current jdk version.
 * 
 * Postcondition: The column "Currently Active" will update in the database.
 * Successful condition status update of the patient.
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
@WebServlet("/UpdateConditionCompletionServlet")
public class UpdateConditionCompletionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";
	
	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String patientID = request.getParameter("patientID");
		String conditionID = request.getParameter("conditionID");
		String visitID = request.getParameter("visitID");
		boolean currentlyActive = (request.getParameter("currentlyactive").contains("Yes"));
		String conditionStatus = "0";
		boolean completionStatus = (request.getParameter("completionStatus").contains("Yes"));
		String completeStatus = "0";

		if(currentlyActive)
		{
			conditionStatus = "1";
		}
		if(completionStatus)
		{
			completeStatus = "1";
		}
		
		String sql = "UPDATE conditions SET currentlyActive = " + conditionStatus + " WHERE ConditionID = ? AND PatientID = ?;";
		String addSql = "UPDATE visit SET Completed = " + completeStatus + " WHERE PatientID = ? AND VisitID = ?;";
	
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
			pstmt.setString(1, conditionID);
			pstmt.setString(2, patientID);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(addSql);
			pstmt.setString(1, patientID);
			pstmt.setString(2, visitID);
			pstmt.executeUpdate();
	
			int conditionrows = pstmt.executeUpdate();
			int completionrows = pstmt.executeUpdate();
			conn.commit();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<p>" + conditionrows + " Condition Rows Updated. " + completionrows + " Visit Rows Updated.<br/>Condition and Completion Status Saved.</p>");
			
			pstmt.close();
			conn.close();
			out.flush();
		} catch (Exception e) {
			// Handle errors
			e.printStackTrace();
		} // end try
	}
}