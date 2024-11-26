import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateRecords")
public class UpdateRecords extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        // Generate HTML form for user input
        String htmlForm = "<html><head><title>Update Records</title>"
                + "<style>"
                + "body { display: flex; justify-content: center; align-items: center; height: 100vh; }"
                + ".form-container { border: 2px solid black; padding: 20px; border-radius: 30px;}"
                + "</style>"
                + "</head><body>"
                + "<div class='form-container'>"
                + "<h2>Update Records</h2><br>"
                + "<form action=\"UpdateRecords\" method=\"post\">"
                + "Pancard No: <input type=\"text\" name=\"pan\"><br><br>"
                + "First Name: <input type=\"text\" name=\"fname\"><br><br>"
                + "Last Name: <input type=\"text\" name=\"lname\"><br><br>"
                + "Email: <input type=\"text\" name=\"email\"><br><br>"
                + "Phone Number: <input type=\"text\" name=\"phone\"><br><br>"
                + "<label for=\"course\">Course:</label>\r\n"
                + "<select id=\"course\" name=\"course\">\r\n"
                + "<option value=\"java\">Java</option>\r\n"
                + "<option value=\"python\">Python</option>\r\n"
                + "<option value=\"testing\">Testing</option>\r\n"
                + "<option value=\"sql\">SQL</option>\r\n"
                + "</select><br><br>"
                + "<label class=\"gender\">\r\n"
                + "<input type=\"radio\" name=\"gender\" value=\"male\">\r\n"
                + "Male\r\n"
                + "</label>\r\n"
                + "<label class=\"gender\">\r\n"
                + "<input type=\"radio\" name=\"gender\" value=\"female\">\r\n"
                + "Female\r\n"
                + "</label>\r\n"
                + "<label class=\"gender\">\r\n"
                + "<input type=\"radio\" name=\"gender\" value=\"other\">\r\n"
                + "Other\r\n"
                + "</label><br><br>"
                + "<input type=\"submit\" value=\"Update\">"
                + "</form> <br>"
                + "<br>  <center><a href='ViewRecords'><h2>view Records</h2></a></center>"
                + "</div>"
                + "</body></html>";


        response.getWriter().println(htmlForm);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String pan = request.getParameter("pan");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String course = request.getParameter("course");
        String gender = request.getParameter("gender");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "pass");

            PreparedStatement stmt = con.prepareStatement(
                "UPDATE studentdetails SET F_name=?, L_name=?, E_mail=?, Phone_no=?, course=?, Gender=? WHERE Pan_no = ?");
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, course);
            stmt.setString(6, gender);
            stmt.setString(7, pan);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.getWriter().println("<html><head><title>Success</title></head><body>");
                response.getWriter().println("<h2>Record with Pan No '" + pan + "' updated successfully</h2>");
                response.getWriter().println("</body></html>");
            } else {
                response.getWriter().println("<html><head><title>Error</title></head><body>");
                response.getWriter().println("<h2>No records found with Pan no '" + pan + "'</h2>");
                response.getWriter().println("</body></html>");
            }

            con.close();
            
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
