/*
 * Greg Brown
 * SAGA (Team 2)
 * 
 * The following program retrieves patient name and procedure
 * information from a view intended to give an overview
 * of current ongoing conditions
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
@WebServlet("/HomeUpdateServlet")
public class HomeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "sesame80";
	
	// SQL statements
	String sql = "SELECT * FROM RecentActivity";

	protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
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
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<h1>Recent Occurrences</h1>");
			out.println("<a href=\"Home.html\">Directory</a>");
			
			out.println("<table> <tr><th>Last Name</th><th>First Name</th> <th>Condition</th><th>Visit Date</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString("LastName")+"</td>");
				out.println("<td>"+rs.getString("FirstName")+"</td>");
				out.println("<td>"+rs.getString("ConditionDescription")+"</td>");
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