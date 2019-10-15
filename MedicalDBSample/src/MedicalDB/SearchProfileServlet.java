/*
 * Dalia Faria
 * SAGA (Team 2)
 * 
 * This program interacts with the "Search for Patient" form. 
 * The user can search for a patient based on the following fields.
 * Not all information is necessary to perform a search.
 * 
 * Precondition: No fields are required. PatientProfile.html
 * and database schema must be set up prior to
 * program run. Root password must be set to local server
 * and jdk version must be set to user's jdk version.
 * 
 * Postcondition: An overview of all patients in the database
 * will show if no input fields contain any value. Search will
 * run based on which fields are defined.
 * 
 */


package MedicalDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Core servlet implementation
 */
@WebServlet("/SearchProfileServlet")
public class SearchProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";
	
	// SQL statements
	String sql = "SELECT * FROM patient WHERE FirstName LIKE ? AND LastName Like ?"
			+ "AND PatientID LIKE ? AND Bday LIKE ? AND Phone LIKE ?";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String firstName = request.getParameter("patientFirstName");
		String lastName = request.getParameter("patientLastName");
		String patientID = request.getParameter("patientID");
		String birthYear = request.getParameter("birthday");
		String phoneNumber = request.getParameter("phone");

		if (firstName.length() == 0)
			firstName = "%";
		if (lastName.length() == 0)
			lastName = "%";
		if (patientID.length() == 0)
			patientID = "%";
		if (birthYear.length() == 0)
			birthYear = "%";
		if (phoneNumber.length() == 0)
			phoneNumber = "%";
		
		
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
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, patientID);
			pstmt.setString(4, birthYear);
			pstmt.setString(5, phoneNumber);
			
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<h1>Patient Results</h1>");
			out.println("<table> <tr><th>First Name</th><th>Last Name</th><th>Patient ID</th> <th>Birth Year</th>"
					+ "<th>Phone Number</th><th>Emergency First Name</th><th>Emergency Last Name</th>"
					+ "<th>Weight(lbs)</th><th>Height(in)</th><th>Blood Type</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString("PatientID")+"</td>");
				out.println("<td>"+rs.getString("FirstName")+"</td>");
				out.println("<td>"+rs.getString("LastName")+"</td>");
				out.println("<td>"+rs.getString("Bday")+"</td>");
				out.println("<td>"+rs.getString("Phone")+"</td>");
				out.println("<td>"+rs.getString("EmergencyFirstName")+"</td>");
				out.println("<td>"+rs.getString("EmergencyLastName")+"</td>");
				out.println("<td>"+rs.getString("Weight")+"</td>");
				out.println("<td>"+rs.getString("Height")+"</td>");
				out.println("<td>"+rs.getString("BloodType")+"</td>");
				out.println("</tr>");
			}
			rs.close();
			out.println("</table>");
			out.println("</body></html>");
			
			pstmt.close();
			conn.close();
			out.flush();
		} catch (Exception e) {
			// Handle errors
			e.printStackTrace();
		} // end try
	}
}