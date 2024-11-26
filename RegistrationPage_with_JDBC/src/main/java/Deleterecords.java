
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Deleterecords")
public class Deleterecords extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

     
        String htmlForm = "<html><head><title>Delete Records</title>"
                + "<style>"
                + "body { display: flex; justify-content: center; align-items: center; height: 100vh; }"
                + ".form-container { border: 2px solid black; border-radius: 25px; padding: 20px; box-sizing: border-box; }"
                + "</style>"
                + "</head><body>"
                + "<div class='form-container'>"
                + "<h2>Delete Records</h2><br>"
                + "<form action=\"Deleterecords\" method=\"post\">"
                + "Pancard No: <input type=\"text\" name=\"pan\"><br><br>"
                + "<input type=\"submit\" value=\"Delete\">"
                + "</form>"
                + "<br><center><h3><a href='ViewRecords'>View Records</a></h3></center>"
                + "</div>"
                + "</body></html>";

        
        response.getWriter().println(htmlForm);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String pan = request.getParameter("pan");


        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "pass");

            PreparedStatement stmt = con.prepareStatement("DELETE FROM studentdetails WHERE Pan_no = ?");
            stmt.setString(1, pan); 

            int rowsDeleted = stmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                response.getWriter().println("<html><head><title>Success</title></head><body>");
                response.getWriter().println("<h2 style='color: red;'>Record with Pan No '" + pan + "' deleted successfully</h2>");

                response.getWriter().println("</body></html>");
            } else {
                response.getWriter().println("<html><head><title>Error</title></head><body>");
                response.getWriter().println("<h2 style='color: red;'>No records found with Pan no '" + pan + "'</h2>");
               
                response.getWriter().println("</body></html>");
            }
           

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
}
}