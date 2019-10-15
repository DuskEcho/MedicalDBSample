/*
 * Dalia Faria
 * SAGA (Team 2)
 * 
 * This program interacts with the "Log New Patient" form. 
 * Upon submission, the values will update
 * into the medical database, successfully creating a new patient.
 * 
 * Precondition: All fields are required. PatientProfile.html
 * and database schema must be set up prior to
 * program run. Root password must be set to local server
 * and jdk version must be set to user's jdk version.
 * 
 * Postcondition: The input values will store into the
 * medical database. Successful creation of new patient.
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
@WebServlet("/AddProfileServlet")
public class AddProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Dab23388!";
	
	// SQL statements
	String sql = "INSERT INTO patient(FirstName, LastName, Bday, Phone, EmergencyFirstName, EmergencyLastName, Weight, Height, BloodType)\r\n" + 
			"values(?,?,?,?,?,?,?,?,?);";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String firstName = request.getParameter("patientFirstName");
		String lastName = request.getParameter("patientLastName");
		String birthYear = request.getParameter("birthday");
		String phoneNumber = request.getParameter("phone");
		String emFirst = request.getParameter("emergencyFirst");
		String emLast = request.getParameter("emergencyLast");
		String weight = request.getParameter("weight");
		String height = request.getParameter("height");
		String blood = request.getParameter("bloodtype");

		if (firstName == "")
			firstName = null;
		if (lastName == "")
			lastName = null;
		if (birthYear == "")
			birthYear = null;
		if (phoneNumber == "")
			phoneNumber = null;
		if (weight == "")
			weight = null;
		if (height == "")
			height = null;
		if (blood == "")
			blood = null;
		
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
			pstmt.setString(3, birthYear);
			pstmt.setString(4, phoneNumber);
			pstmt.setString(5, emFirst);
			pstmt.setString(6, emLast);
			pstmt.setString(7, weight);
			pstmt.setString(8, height);
			pstmt.setString(9, blood);
			
			int nrows = pstmt.executeUpdate();
			conn.commit();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<p>" + nrows + " Rows Updated. New Patient Information Saved.</p>");
			out.println("<a href=\"PatientVisits.html\">Click Here to Make Appointment</a>");
			
			pstmt.close();
			conn.close();
			out.flush();
		} catch (Exception e) {
			// Handle errors
			e.printStackTrace();
		} // end try
	}
}