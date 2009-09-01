<%@ page 
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Forums List</title>
  </head>
  <body>
    <ul id="forums_menu">
      <li><a href="<c:url value="/forum/create" />">Create a new Forum</a></li>
    </ul>
    <c:forEach items="${forums}" var="forum">
    <p>
      <a href="<c:url value="/forum/" /><c:out value="${forum.name}" escapeXml="true" />">${forum.name}</a> <br />
      ${forum.description} <br />
    </p>
    </c:forEach>
  </body>
</html>