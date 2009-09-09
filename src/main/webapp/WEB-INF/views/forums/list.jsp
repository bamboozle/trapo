<%@ page 
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Forums List</title>
  </head>
  <body>
    <ul id="forums_menu">
      <li><a href="<c:url value="/" />">Home</a></li>
      <li><a href="<c:url value="/forum/create" />">Create a new Forum</a></li>
    </ul>
    <table>
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Created At</th>
      </tr>
      <c:forEach items="${forums}" var="forum">
      <c:set var="temp" value="${forum.name}" scope="request" />
      <%
        String encodedForumName = (String)request.getAttribute("temp");
        encodedForumName  = java.net.URLEncoder.encode(encodedForumName, "utf-8");
        request.setAttribute("encodedForumName", encodedForumName);
      %>
      <tr>
        <td><a href="<c:url value="/forum/${encodedForumName}" />">${forum.name}</a></td>
        <td>${forum.description}</td>
        <td>${forum.createdAt}</td>
      </tr>
      </c:forEach>
    </table>
  </body>
</html>