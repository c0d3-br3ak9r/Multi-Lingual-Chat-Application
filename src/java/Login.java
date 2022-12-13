/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/index.html");
        view.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userName = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        
        if ( userName.equals("") || password.equals("") ) {
            response.sendRedirect("Login");
            return;
        }
        
        PrintWriter pw = response.getWriter();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:9906/","user","password");
            stmt = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if ( rs.next() ) {
                response.sendRedirect("Home");
            } else {
                response.sendRedirect("Login");
                return;
            }
        }
        catch(Exception e) {pw.write(e.toString());}
    }
}
