/*
 * Andrew Terrado
 * SAGA (Team 2)
 * 
 * This servlet generates physician information based off of fragments
 * of information entered on the physician.html page. Based on 
 * entered information, relevant physician information is displayed
 * Or, if no information is entered, a directory of physicians is generated
 * 
 * 
 * Precondition: The database must be populated and formatted. There
 * must be physician information to view.

 * 
 * Postcondition: If successful, specified physician information is displayed
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

@WebServlet("/PhysicianSearchServlet")
public class PhysicianSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";

	// SQL statements
	String sql = "SELECT * FROM physicians WHERE PhysicianID LIKE ? AND PhysicianName LIKE ?"
			+ "AND ContactNumber LIKE ?";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String DrID = request.getParameter("physicianID");
		String DrName = request.getParameter("physicianName");
		String DrNumber = request.getParameter("contactNumber");

		if (DrID.length() == 0)
			DrID = "%";
		if (DrName.length() == 0)
			DrName = "%";
		if (DrNumber.length() == 0)
			DrNumber = "%";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			; // serializeable
			conn.setAutoCommit(false);

			// sql statement
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DrID);
			pstmt.setString(2, DrName);
			pstmt.setString(3, DrNumber);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();

			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<table> <tr><th>Physician ID</th><th>Physician Name</th><th>Contact Number</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("PhysicianID") + "</td>");
				out.println("<td>" + rs.getString("PhysicianName") + "</td>");
				out.println("<td>" + rs.getString("ContactNumber") + "</td>");
				out.println("</tr>");
			}
			rs.close();
			out.println("</table>");
			out.println("</br>");
			out.println("<a href=\"Physician.html\">Back to Physician Directory</a>");
			out.println("</br>");
			out.println("</br>");
			out.println("<a href=\"Home.html\">Back to Home Page</a>");
			out.println("</br>");
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