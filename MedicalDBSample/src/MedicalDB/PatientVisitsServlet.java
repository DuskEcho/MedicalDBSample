/*
 * Greg Brown
 * SAGA (Team 2)
 * 
 * The following servlet retrieves visits
 * from the MedicalDB using JDBC and MySQL.
 * Visits are displayed beginning with the most 
 * recent. Data is pulled from the PatientVisits.html 
 * page.
 * 
 * 
 * Precondition: The database is in place and accessible
 * with the proper tables and columns
 * 
 * Postcondition: on success, the requested
 * visit list will be displayed as HTML
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
@WebServlet("/PatientVisitsServlet")
public class PatientVisitsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "sesame80";
	
	// SQL statements
	String originalSql = "SELECT FirstName, LastName, ph.PhysicianName as PhysicianName, VisitDescription, VisitDate\r\n" + 
			"FROM Patient p \r\n" + 
			"JOIN visit v ON p.PatientId = v.PatientId " +
			"JOIN physicians ph on ph.`PhysicianID` = v.`PhysicianID`"
			+ " WHERE FirstName LIKE ? AND LastName LIKE ? AND ";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String firstName = request.getParameter("patientFirstName");
		String lastName = request.getParameter("patientLastName");
		boolean visitComplete = (request.getParameter("visitCompleted").contains("Yes"));
		String visitCompleteBool = "false";
		String completionStatus = "Incomplete Visits";
		if (visitComplete)
		{
			completionStatus = "Completed Visits";
			visitCompleteBool = "true";
		}
		if (firstName.length() == 0)
			firstName = "%";
		if (lastName.length() == 0)
			lastName = "%";
		
		String sql = originalSql + "v.Completed = " + visitCompleteBool + " ORDER BY VisitDate DESC;";
		
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
			
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<h1>" + completionStatus + "</h1>");
			out.println("<table> <tr><th>First Name</th><th>Last Name</th><th>Physician</th> <th>Visit Summary</th><th>Visit Date</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString("FirstName")+"</td>");
				out.println("<td>"+rs.getString("LastName")+"</td>");
				out.println("<td>"+rs.getString("PhysicianName")+"</td>");
				out.println("<td>"+rs.getString("VisitDescription")+"</td>");
				out.println("<td>"+rs.getString("VisitDate")+"</td>");
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
