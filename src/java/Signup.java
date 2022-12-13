import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AnotherUser
 */
@WebServlet(urlPatterns = {"/Signup"})
public class Signup extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/signup.html");
        view.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userName = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        String cPassword = request.getParameter("c-password").trim();
        
        if ( ( userName.equals("") || email.equals("") || password.equals("") || cPassword.equals("") ) ||
                ( !password.equals(cPassword) ) ) {
            response.sendRedirect("Signup");
            return;
        }
        
        PrintWriter pw = response.getWriter();
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:9906/","user","password");
            stmt = con.prepareStatement("INSERT INTO `users`(`username`, `password`, `email`) VALUES (?, ?, ?)");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            stmt.setString(3, email);
            int rs = stmt.executeUpdate();
            
            if ( rs > 0 ) {
                response.sendRedirect("Login");
            }
        }
        catch(Exception e) {pw.write(e.toString());}
    }
}
