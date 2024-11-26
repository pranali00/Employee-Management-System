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

@WebServlet("/ViewRecords")
public class ViewRecords extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>View Records</title></head><body>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "pass");

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM studentdetails");
            ResultSet rs = stmt.executeQuery();

            out.println("<center> <h2 style='color: red;'>Registered Students</h2> </center>");

            out.println("<br>");
            out.println("<table border='1' width='100%'");
            out.println("<tr><th>First Name</th><th>Last Name</th><th>Email</th><th>Phone</th><th>Course</th><th>Gender</th><th>Pancard No</th><th>Edit</th><th>Delete</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString(1) + "</td>");
                out.println("<td>" + rs.getString(2) + "</td>");
                out.println("<td>" + rs.getString(3) + "</td>");
                out.println("<td>" + rs.getString(4) + "</td>");
                out.println("<td>" + rs.getString(5) + "</td>");
                out.println("<td>" + rs.getString(6) + "</td>");
                out.println("<td>" + rs.getString(7) + "</td>");
        
                out.println("<td><a href='UpdateRecords?id=" + rs.getString(7) + "'>Update</a></td>");
             
                out.println("<td><a href='Deleterecords?pan=" + rs.getString(7) + "'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");



            con.close(); 
            out.println("<br>");
            out.println("<div style='display: flex; justify-content: center;'>");  
            out.println("<a href='index.html' style='color: grey;'><h3>Registration Page</h3></a>");        
            out.println("</div>");

            
        } catch (Exception e) {
            System.out.println(e);
        }

        out.println("</body></html>");
        out.close();
    }
}
