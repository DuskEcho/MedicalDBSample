/*
 * Greg Brown
 * SAGA (Team 2)
 * 
 * The following servlet logs a new "procedure"
 * to the MedicalDB using JDBC and MySQL. 
 * Data is pulled from the PatientProcedures.html 
 * page.  On success, the user is notified of the 
 * number of updated rows (should always be 1 if
 * successful). 
 * 
 * 
 * Precondition: The database is in place and accessible
 * with the proper tables and columns
 * 
 * Postcondition: on success, the database
 * will be modified with an additional row.
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
@WebServlet("/AddProcedureServlet")
public class AddProcedureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "sesame80";
	
	// SQL statements
	String usql = "INSERT INTO procedures (PatientID, ProcedureDescription, ProcedureDate, ConditionID, Result)\r\n" + 
			"VALUES ( \r\n" + 
			"(SELECT PatientID FROM patient WHERE FirstName = ? AND LastName = ?), ?, ?, ?, ?);";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String firstName = request.getParameter("patientFirstName");
		String lastName = request.getParameter("patientLastName");
		String description = request.getParameter("procedureDescription");
		String procedureDate = request.getParameter("procedureDate");
		String conditionID = request.getParameter("conditionID");
		String result = request.getParameter("result");
		
		response.setContentType("text/html");   
		PrintWriter out = response.getWriter();

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			conn = DriverManager.getConnection(
                  DB_URL, USER, PASS);


			// now prepare and execute update or insert 
			// sql statement
			pstmt = conn.prepareStatement(usql);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, description);
			pstmt.setString(4, procedureDate);
			pstmt.setString(5, conditionID);
			pstmt.setString(6, result);

			int nrows = pstmt.executeUpdate();

			
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