<%-- Login page (no header navigation since user is not logged in yet) --%>
<%
// If user is already logged in (session exists), redirect to gallery to skip login
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/gallery");
        return;
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    </head>
    <body>
        <h2>Login</h2>
        <p class="error">${errorMessage}</p>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="$
                       {username}" required />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required />
            </div>
            <button type="submit">Login</button>
        </form>
    </body>
</html>
