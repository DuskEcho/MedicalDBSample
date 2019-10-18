/* 
 * Dalia Faria
 * SAGA (Team 2)
 * 
 * This program interacts with the "Find Patient Based on Condition History" form. 
 * The user can search for a patient by condition
 * based on the following fields.
 * Not all information is necessary to perform a search.
 * 
 * Precondition: No fields are required. PatientConditions.html
 * and database schema must be set up prior to
 * program run. Server credentials must be set to "user" and 
 * password must be set to local host's password or "sesame80" as below.
 * Jdk version must be set to your current jdk version.
 * 
 * Postcondition: An overview of all patients' condition history 
 * in the database will show if no input fields contain any value. 
 * Search will run based on which fields are defined.
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
@WebServlet("/FindOnConditionServlet")
public class FindOnConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/medicaldb";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "sesame80";
	
	// SQL statements
	String sql = "SELECT FirstName, LastName, c.PatientID, ConditionDescription, ConditionID, CurrentlyActive \r\n" + 
			"FROM conditions c JOIN patient p ON c.PatientID = p.PatientID\r\n" + 
			"WHERE ConditionDescription LIKE ? AND ConditionID LIKE ? ORDER BY CurrentlyActive;";

	protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String conditionDescription = request.getParameter("conditionDescription");
		String conditionID = request.getParameter("conditionID");

		if (conditionDescription.length() == 0)
			conditionDescription = "%";
		else
			conditionDescription = "%" + conditionDescription + "%";
		if (conditionID.length() == 0)
			conditionID = "%";

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
			pstmt.setString(1, conditionDescription);
			pstmt.setString(2, conditionID);
			
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<h1>Patient Based on Condition Results</h1>");
			out.println("<table> <tr><th>First Name</th><th>Last Name</th><th>Patient ID</th> <th>Condition Description</th>"
					+ "<th>Condition ID</th><th>Currently Active</th></tr>");
			out.println("<p>For the column \"Currently Active\" - 0 means NO, 1 means YES.</p>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString("FirstName")+"</td>");
				out.println("<td>"+rs.getString("LastName")+"</td>");
				out.println("<td>"+rs.getString("PatientID")+"</td>");
				out.println("<td>"+rs.getString("ConditionDescription")+"</td>");
				out.println("<td>"+rs.getString("ConditionID")+"</td>");
				out.println("<td>"+rs.getString("CurrentlyActive")+"</td>");
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