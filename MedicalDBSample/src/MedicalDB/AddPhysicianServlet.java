/*
 * Andrew Terrado
 * SAGA (Team 2)
 * 
 * This servlet inserts a new physician into
 * the physician table of the MedicalDB. 
 * Information is entered by the user on 
 * physician.html page.  If successful, 
 * confirmation of new physician's name will be displayed.
 * 
 * 
 * Precondition: The database must be 
 * populated and formatted. User must at least
 * enter a physician name.

 * 
 * Postcondition: if successful, new physician
 *  entry (with user submitted information) will be added
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
@WebServlet("/AddPhysicianServlet")
public class AddPhysicianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";

	// SQL statements
	String usql = "INSERT INTO physicians (PhysicianName, ContactNumber)" 
	+"VALUES (?, ?);";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String DrName = request.getParameter("DrName");
		String DrNumber = request.getParameter("DrNumber");
		
		

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
			//If statement to ensure name is not blank
			if (DrName.length() > 0) {
			pstmt = conn.prepareStatement(usql);
			pstmt.setString(1, DrName);
			pstmt.setString(2, DrNumber);
			
			
			pstmt.executeUpdate();
			}
			out.println("<!DOCTYPE HTML><html><body>");
			conn.commit();
			conn.setAutoCommit(true);

			out.println("<!DOCTYPE HTML><html><body>");

			out.println("</br>");
			out.println("<strong>New Physician Entry: </strong>" + DrName);
			out.println("</br>");
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
