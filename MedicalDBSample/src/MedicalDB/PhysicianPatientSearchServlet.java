/*
 * Andrew Terrado
 * SAGA (Team 2)
 * 
 * This servlet uses the visit and physician table to generate a log
 * of all physician visits. Specific physicians can be selected 
 * using data entered on the physician.html page. 
 * 
 * 
 * Precondition: The database must be populated and formatted.

 * 
 * Postcondition: if successful, a list of specified doctor visits
 * are displayed to the user.
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
@WebServlet("/PhysicianPatientSearchServlet")
public class PhysicianPatientSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";

	// SQL statements
	String sql = "SELECT PhysicianName as PhysicianName, FirstName, LastName, VisitDate, v.physicianID "
			+ "FROM physicians " + "NATURAL JOIN visit v " + "Join patient  ON v.PhysicianID = physicians.PhysicianID "
			+ "WHERE v.PhysicianID LIKE ? and PhysicianName LIKE ?" + "ORDER BY PhysicianName, visitDate";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String DrID = request.getParameter("DrID");
		String DrName = request.getParameter("DrName");

		if (DrID.length() == 0)
			DrID = "%";
		if (DrName.length() == 0)
			DrName = "%";

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

			// now prepare and execute update or insert
			// sql statement
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DrID);
			pstmt.setString(2, DrName);
			ResultSet rs = pstmt.executeQuery();
			conn.commit();

			out.println("<!DOCTYPE HTML><html><body>");
			out.println(
					"<table> <tr><th>Physician ID</th><th>Physician</th><th>Patient Name</th><th>Visit Date</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("PhysicianID") + "</td>");
				out.println("<td>" + rs.getString("PhysicianName") + "</td>");
				out.println("<td>" + rs.getString("FirstName") + (" ") + rs.getString("LastName") + "</td>");
				out.println("<td>" + rs.getString("VisitDate") + "</td>");

				out.println("</tr>");
			}
			rs.close();
			out.println("</table>");
			out.println("<a href=\"Physician.html\">Back to Physician Directory</a>");
			out.println("</br>");
			out.println("</br>");
			out.println("<a href=\"Home.html\">Back to Home Page</a>");
			out.println("</br>");
			out.println("</body></html>");
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
