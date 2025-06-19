package com.example.gallery;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// If user is already logged in, go to gallery directly
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/gallery");
        } else {
            request.getRequestDispatcher("login.jsp").forward(request,
                    response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
// Basic validation of input fields
        if (username == null || password == null
                || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter username and password.");

       
request.setAttribute("username", username);
            request.getRequestDispatcher("login.jsp").forward(request,
                    response);
            return;
        }
        User user = UserDAO.validateUser(username, password);
        if (user != null) {
// Login successful: create session and store user
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/gallery");
        } else {
// Login failed: return to login page with error
            request.setAttribute("errorMessage", "Invalid username or password.");
request.setAttribute("username", username);
            request.getRequestDispatcher("login.jsp").forward(request,
                    response);
        }
    }
}
