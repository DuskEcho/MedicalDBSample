/*
 * Greg Brown
 * SAGA (Team 2)
 * 
 * The following program retrieves patient name and procedure
 * information from a separate database using MySQL and JDBC.
 * Minimal exception handling is present; only the stack trace is 
 * retained.
 * 
 * 
 * Precondition: The database is in place and accessible
 * with the proper tables and columns
 * 
 * Postcondition: on success, the requested
 * procedures list will be displayed as HTML
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
@WebServlet("/PatientProceduresServlet")
public class PatientProceduresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";
	
	// SQL statements
	String sql = "SELECT FirstName, LastName, ProcedureDescription, ProcedureDate, ph.PhysicianName as PhysicianName "
			+ "FROM patient "
			+ "NATURAL JOIN procedures p "
			+ "Join physicians ph  ON p.PhysicianID = ph.PhysicianID "
			+ "WHERE FirstName LIKE ? AND LastName LIKE ? AND ProcedureID LIKE ?";

	protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String firstName = request.getParameter("patientFirstName");
		String lastName = request.getParameter("patientLastName");
		String procedureCode = request.getParameter("procedureCode");
		if (procedureCode.length() == 0)
			procedureCode = "%";
		if (firstName.length() == 0)
			firstName = "%";
		if (lastName.length() == 0)
			lastName = "%";
		
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
			pstmt.setString(3, procedureCode);
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<table> <tr><th>First Name</th><th>Last Name</th> <th>Procedure Description</th><th>Procedure Date</th><th>Physician</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString("FirstName")+"</td>");
				out.println("<td>"+rs.getString("LastName")+"</td>");
				out.println("<td>"+rs.getString("ProcedureDescription")+"</td>");
				out.println("<td>"+rs.getString("ProcedureDate")+"</td>");
				out.println("<td>"+rs.getString("PhysicianName")+"</td>");
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
			out.println("<h1>Check your input, try again.</h1>");
		} // end try
	}
}
