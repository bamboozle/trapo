<%@ page 
  language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://trapo.posterous.com/tags" prefix="trapo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/forums/list.css" />" charset="utf-8">
    <title>${topic.title} - Trapo</title>
    <style type="text/css">
      .crumb {
        margin-left: 0px;
        font-size: 1.2em;
        text-transform: uppercase;
      }
      .crumb li {
        display: inline;
      }
    </style>
  </head>
  <body>
    <div class="grid_24">
      <c:if test="${message != null}">
        <p class="grid_12 ${message.type}">${message}</p>
      </c:if>
    </div>
    <div class="grid_24">
      <ul class="crumb">
        <li><a href="<trapo:beauty url="/view/forum/" value="${forum.name}" extension="html" />">${forum.name}</a></li>
        <li class="separator">/</li>
        <li>${topic.title}</li>
      </ul>
      <p>${topic.text}</p>
      <p>${topic.createdAt}</p>
    </div>
    <div class="grid_24">
      <form action="<c:url value="/view/topic/reply/" />" method="get" name="replytopic">
        <input type="hidden" name="id" value="${topic.id}">
        <input type="submit" value="Reply Topic" />
      </form>
    </div>
  </body>
</html>