<%@ page 
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="sm" %>
<%@ taglib uri="http://trapo.posterous.com/taglib" prefix="url" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Forums List</title>
  </head>
  <body>
    <ul id="forums_menu">
      <li><a href="<c:url value="/" />">Home</a></li>
      <li><a href="<c:url value="/view/forum/create" />">Create a new Forum</a></li>
    </ul>
    <c:if test="${message != null}">
      <p class="${message.type}">${message}</p>
    </c:if>
    <table>
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Created At</th>
      </tr>
      <c:forEach items="${forums}" var="forum">
      <tr>
        <td><a href="<c:url value="/view/forum/" /><url:encode value="${forum.name}" />">${forum.name}</a></td>
        <td>${forum.description}</td>
        <td>${forum.createdAt}</td>
      </tr>
      </c:forEach>
    </table>
  </body>
</html>