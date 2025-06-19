<%@ page import="java.util.List, com.example.gallery.Image" %>
<%
// Redirect to login if not authenticated
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Top Images</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h2>Top N Images</h2>
        <p class="error">${errorMessage}</p>
        <form action="${pageContext.request.contextPath}/top" method="get">
            <div class="form-group">

                <label for="N">Number (N):</label>
                <input type="number" id="N" name="N" min="1" value="${N}" required /
                       >
            </div>
            <button type="submit">Show Top N</button>
        </form>
        <%
            List<Image> topList = (List<Image>) request.getAttribute("topList");
            Integer N = (Integer) request.getAttribute("N");
            if (topList != null) {
                if (topList.isEmpty()) {
        %>
        <p>No images available.</p>
        <%
        } else {
        %>
        <h3>Top <%= N%> Images</h3>
        <table class="gallery-table">
            <tr>
                <th>Rank</th>
                <th>Image</th>
                <th>Votes</th>
                <th>Author</th>
            </tr>
            <%
                int rank = 1;
                for (Image img : topList) {
            %>
            <tr>
                <td><%= rank++%></td>
                <td><img src="<%= request.getContextPath() + "/"
        + img.getFilePath()%>" alt="image" /></td>
                <td><%= img.getVoteCount()%></td>
                <td><%= img.getUsername()%></td>
            </tr>
            <%
                } // end for
            %>
        </table>
        <%
                } // end if-empty
            } // end if topList not null
        %>
    </body>
</html>