package com.example.gallery;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GalleryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
// not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User currentUser = (User) session.getAttribute("user");
// Retrieve all images and vote counts
        List<Image> images = ImageDAO.getAllImagesWithVotes();
// Retrieve set of image IDs that current user has voted for
        Set<Integer> votedIds
                = VoteDAO.getVotedImagesByUser(currentUser.getId());
        request.setAttribute("images", images);
        request.setAttribute("votedIds", votedIds);

        request.getRequestDispatcher("gallery.jsp").forward(request, response);
    }
}
