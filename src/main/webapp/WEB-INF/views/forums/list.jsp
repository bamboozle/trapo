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
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/forums/list.css" />" charset="utf-8">
    <script type="text/javascript">
      $(function() {
        $("h2").click(function() {
            window.location = $(this).find("a").attr("href");
            return false; 
        });
      });
    </script>
  </head>
  <body>
    <div class="span-11 last">
      <ul id="forums_menu">
        <li><a href="<c:url value="/view/forums" />">Forums</a></li>
        <li><a href="<c:url value="/view/forum/create" />">Create a new Forum</a></li>
      </ul>
    </div>
    <div class="span-24">
      <c:if test="${message != null}">
        <p class="${message.type}">${message}</p>
      </c:if>
    </div>
    <div class="span-24">
      <c:forEach items="${forums}" var="forum" varStatus="index">
      <div class="span-24 last fff">
        <h2><a href="<c:url value="/view/forum/" /><url:encode value="${forum.name}" />">${forum.name}</a></h2>
        <p class="description">${forum.description}</p>
        <p class="createdAt">Created at ${forum.createdAt}</p>
      </div>
      </c:forEach>
    </div>
  </body>
</html>