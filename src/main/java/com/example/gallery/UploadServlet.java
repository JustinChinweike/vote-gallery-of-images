package com.example.gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Handles image uploads, saves file to /uploads and records path in DB.
 */
@MultipartConfig(
        maxFileSize       = 5 * 1024 * 1024,   // 5 MB
        maxRequestSize    = 10 * 1024 * 1024,
        fileSizeThreshold = 0
)
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ---------- session / auth ---------- */
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        /* ---------- read uploaded file ---------- */
        Part filePart = request.getPart("imageFile");          // <input name="imageFile">
        if (filePart == null || filePart.getSubmittedFileName() == null
                || filePart.getSubmittedFileName().isEmpty()) {

            request.setAttribute("errorMessage", "Please choose an image file to upload.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            request.setAttribute("errorMessage", "Only image files are allowed.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        /* ---------- save to /uploads ---------- */
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalName = filePart.getSubmittedFileName();
        String storedName   = System.currentTimeMillis() + "_" + originalName;
        File   diskFile     = new File(uploadDir, storedName);

        try (InputStream in  = filePart.getInputStream();
             FileOutputStream out = new FileOutputStream(diskFile)) {

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error saving the file. Please try again.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        /* ---------- persist DB record ---------- */
        String dbFilePath = "uploads/" + storedName;     // value stored in DB
        boolean ok = ImageDAO.addImage(dbFilePath, currentUser.getId());
        if (!ok) {                                       // rollback file if DB insert fails
            diskFile.delete();
            request.setAttribute("errorMessage", "Failed to save image info to database.");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
            return;
        }

        /* ---------- done ---------- */
        response.sendRedirect(request.getContextPath() + "/gallery");
    }
}
