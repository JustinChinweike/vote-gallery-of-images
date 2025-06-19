package com.example.gallery;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

public class DeleteImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User currentUser = (User) session.getAttribute("user");
        String imageIdParam = request.getParameter("id");
        if (imageIdParam != null) {
            try {
                int imageId = Integer.parseInt(imageIdParam);
                Image img = ImageDAO.getImageById(imageId);
                if (img != null && img.getUserId() == currentUser.getId()) {
// User is the owner of the image -> proceed to delete
                    boolean deleted = ImageDAO.deleteImage(imageId);
                    if (deleted) {
// Delete the image file from the server
                        String contextRoot
                                = getServletContext().getRealPath("/");
                        File file = new File(contextRoot, img.getFilePath());
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            } catch (NumberFormatException e) {
// Ignore invalid id format
            }
        }
// Go back to gallery after attempting deletion
        response.sendRedirect(request.getContextPath() + "/gallery");
    }
}
