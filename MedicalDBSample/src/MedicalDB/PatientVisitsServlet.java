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
 * Servlet implementation class PatientVisitsServlet
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
	String originalSql = "SELECT FirstName, LastName, VisitDescription, VisitDate\r\n" + 
			"FROM Patient p \r\n" + 
			"JOIN visit v ON p.PatientId = v.PatientId"
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


			// now prepare and execute update or insert 
			// sql statement
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			
			ResultSet rs = pstmt.executeQuery();
			
			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<h1>" + completionStatus + "</h1>");
			out.println("<table> <tr><th>First Name</th><th>Last Name</th> <th>Visit Summary</th><th>Visit Date</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString("FirstName")+"</td>");
				out.println("<td>"+rs.getString("LastName")+"</td>");
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