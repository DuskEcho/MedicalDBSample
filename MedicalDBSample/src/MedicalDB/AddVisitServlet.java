/*
 * Greg Brown
 * SAGA (Team 2)
 * 
 * The following servlet schedules a new visit
 * to the MedicalDB Using JDBC and MySQL.
 * Data is pulled from the PatientVisits.html page.
 * Visits are assumed to be in the future
 * and as such will always set "completed" to "false".
 * The user is notified of the number of rows updated 
 * (should always be 1 on success).
 * 

 * Precondition: The database is in place and accessible
 * with the proper tables and columns
 * 
 * Postcondition: on success, the database
 * will be modified with an additional row.
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
@WebServlet("/AddVisitServlet")
public class AddVisitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";
	
	// SQL statements
	String usql = "INSERT INTO visit (PatientID, VisitDescription, VisitDate, PhysicianID, Completed) VALUES (" + 
			"(SELECT PatientID FROM patient WHERE FirstName = ? AND LastName = ?), ?, ?, ?, false);";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String firstName = request.getParameter("patientFirstName");
		String lastName = request.getParameter("patientLastName");
		String description = request.getParameter("visitDescription");
		String visitDate = request.getParameter("visitDate");
		String physician = request.getParameter("dr");
		
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
			pstmt = conn.prepareStatement(usql);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, description);
			pstmt.setString(4, visitDate);
			pstmt.setString(5, physician);

			int nrows = pstmt.executeUpdate();
			conn.commit();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<p>" + nrows + " Rows Updated</p>");
			out.println("<a href=\"Home.html\">Back to the homepage</a>");
			
			pstmt.close();
			conn.close();
			out.flush();
		} catch (Exception e) {
			// Handle errors
			e.printStackTrace();
		} // end try
	}
}
