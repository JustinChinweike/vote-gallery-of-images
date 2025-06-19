package com.example.gallery;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VoteServlet extends HttpServlet {

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
// Verify the image exists and is not owned by the current user
                Image img = ImageDAO.getImageById(imageId);
                if (img != null && img.getUserId() != currentUser.getId()) {
// Check if the current user already voted on this image
                    boolean alreadyVoted
                            = VoteDAO.getVotedImagesByUser(currentUser.getId()).contains(imageId);

                    if (!alreadyVoted) {
                        VoteDAO.addVote(currentUser.getId(), imageId);
                    }
                }
            } catch (NumberFormatException e) {
// Invalid image id parameter – ignore
            }
        }
// After processing, redirect back to the gallery page
        response.sendRedirect(request.getContextPath() + "/gallery");
    }
}
