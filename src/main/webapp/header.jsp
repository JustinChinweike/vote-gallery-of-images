<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<div class="navbar">
    <span>Logged in as <strong>${sessionScope.user.username}</strong></span> |
    <a href="${pageContext.request.contextPath}/gallery">Gallery</a> |
    <a href="${pageContext.request.contextPath}/upload">Upload Image</a> |
    <a href="${pageContext.request.contextPath}/top">Top N</a> |
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
<hr/>