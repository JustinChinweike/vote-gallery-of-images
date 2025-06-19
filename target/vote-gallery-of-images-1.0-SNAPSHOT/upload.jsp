<%@ page import="com.example.gallery.User" %>
<%
// Redirect to login if not logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Upload Image</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h2>Upload Image</h2>
        <p class="error">${errorMessage}</p>
        <form action="${pageContext.request.contextPath}/upload" method="post"
              enctype="multipart/form-data">
            <div class="form-group">
                <label for="imageFile">Choose Image:</label>
                <input type="file" id="imageFile" name="imageFile" accept="image/*"
                       required />
            </div>
            <button type="submit">Upload</button>
        </form>
    </body>
</html>