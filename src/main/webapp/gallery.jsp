<%@ page import="java.util.List,java.util.Set,com.example.gallery.Image,com.example.gallery.User" %>
<%
    // Redirect to login if session is invalid
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image Gallery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@ include file="header.jsp" %>

<h2>Image Gallery</h2>
<%
    List<Image> imageList = (List<Image>) request.getAttribute("images");
    Set<Integer> votedIds = (Set<Integer>) request.getAttribute("votedIds");
    User user = (User) session.getAttribute("user");

    if (imageList == null || imageList.isEmpty()) {
%>
    <p>No images uploaded yet.</p>
<%
    } else {
%>
    <table class="gallery-table">
        <tr>
            <th>Image</th>
            <th>Uploader</th>
            <th>Votes</th>
            <th>Actions</th>
        </tr>
        <% for (Image img : imageList) { %>
        <tr>
            <td>
                <img src="<%= request.getContextPath() + "/" + img.getFilePath() %>" alt="image"/>
            </td>
            <td><%= img.getUsername() %></td>
            <td><%= img.getVoteCount() %></td>
            <td>
                <% if (img.getUserId() == user.getId()) { %>
                    <!-- Owner of the image: provide delete option -->
                    <a href="<%= request.getContextPath() %>/deleteImage?id=<%= img.getId() %>"
                       onclick="return confirm('Delete this image?');">Delete</a>
                <% } else if (!votedIds.contains(img.getId())) { %>
                    <!-- Not voted yet: provide vote option -->
                    <a href="<%= request.getContextPath() %>/vote?id=<%= img.getId() %>">Vote</a>
                <% } else { %>
                    <!-- Already voted -->
                    <span class="disabled">Voted</span>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
<% } %>
</body>
</html>
