package com.example.gallery;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;

        }
        String nParam = request.getParameter("N");
        if (nParam != null) {
            try {
                int N = Integer.parseInt(nParam);
                if (N < 1) {
                    request.setAttribute("errorMessage", "Please enter a positive number.");

} else {
List<Image> topImages = ImageDAO.getTopImages(N);
                    request.setAttribute("topList", topImages);
                    request.setAttribute("N", N);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Please enter a valid number.");       
}
}
request.getRequestDispatcher("top.jsp").forward(request, response);
    }
}
