<%@ page 
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><decorator:title default="Trapo" /></title>
    <decorator:head />
  </head>
  <body>
    <div id="navigation">
      <ul>
        <li><a href="<c:url value="/forums/" />">Forums</a></li>
        <li><a href="<c:url value="/tags/" />">Tags</a></li>
        <li><a href="<c:url value="/search/" />">Advanced Search</a></li>
        <c:if test="${user != null}">
        <li><a href="<c:url value="/profile/" />">Profile</a></li>
        <li><a href="<c:url value="/logout/" />">Logout</a></li>
        </c:if>
        <c:if test="${user == null}">
        <li><a href="<c:url value="/login/" />">Login</a></li>
        </c:if>
      </ul>
    </div>
    <div id="main">
      <decorator:body />
    </div>
    <div id="footer">
      <p id="online">Current online people:
        <c:forEach items="${users}" var="onlineUser">
          <a href="<c:url value="/user/${onlineUser.login}" />">${onlineUser.name}</a>
        </c:forEach>
      </p>
      <p id="copyright">2009</p>
      <p id="powered"><a href="http://trapo.posterous.com">Trapo Blog</a> | <a href="http://github.com/bamboozle/trapo">Trapo Github</a></p>
    </div>
  </body>
</html>