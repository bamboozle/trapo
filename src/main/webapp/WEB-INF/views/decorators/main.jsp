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
    <link rel="stylesheet" href="<c:url value="/css/reset.css" />" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="<c:url value="/css/layout.css" />" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="<c:url value="/css/trapo.css" />" type="text/css">
    <script type="text/javascript" src="<c:url value="/javascript/jquery-1.3.2.js" />" ></script>
    <sm:head />
  </head>
  <body class="container">
    <div id="header" class="grid_24">
      <h1><a href="<c:url value="/index.html" />">Trapo Forum</a></h1>
      <p>A forum powered by Trapo</p>
    </div>
    <div id="menu" class="grid_12">
      <ul>
        <li><a href="<c:url value="/index.html" />">Home</a></li>
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
    </div>
    <div id="main">
      <sm:body />
    </div>
    <br />
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