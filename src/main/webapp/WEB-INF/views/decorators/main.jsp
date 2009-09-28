<%@ page 
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="sm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
    <title><sm:title default="Trapo" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/trapo.css" />" media="screen" >
    <sm:head />
  </head>
  <body>
    <div id="header">
      <h1>Trapo Forum</h1>
      <p>A Forum powered by Trapo</p>
    </div>
    <ul id="menu">
      <li><a href="<c:url value="/view/forums/" />">Forums</a></li>
      <li><a href="<c:url value="/view/tags/" />">Tags</a></li>
      <li><a href="<c:url value="/view/search/" />">Advanced Search</a></li>
      <c:choose>
        <c:when test="${user != null}">
          <li><a href="<c:url value="/view/profile/" />">Profile</a></li>
          <li><a href="<c:url value="/view/logout/" />">Logout</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="<c:url value="/view/login/" />">Login</a></li>
        </c:otherwise>
      </c:choose>
    </ul>
    <div id="main">
      <sm:body />
    </div>
    <div id="footer">
      <p id="online">Current online people:
        <c:forEach items="${users}" var="onlineUser">
          <a href="<c:url value="/view/user/${onlineUser.login}" />">${onlineUser.name}</a>
        </c:forEach>
      </p>
      <p id="copyright">2009</p>
      <p id="powered"><a href="http://trapo.posterous.com">Trapo Blog</a> | <a href="http://github.com/bamboozle/trapo">Trapo Github</a></p>
    </div>
  </body>
</html>