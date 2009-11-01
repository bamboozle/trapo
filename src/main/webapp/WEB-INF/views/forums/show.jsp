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
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/forums/list.css" />" charset="utf-8">
    <title>${forum.name} Forum - Trapo</title>
  </head>
  <body>
    <div class="suffix_1">
      <ul id="forums_menu">
        <li><a href="<c:url value="/view/forums" />">Forums</a></li>
        <li><a href="<c:url value="/view/forum/create" />">Create a new Forum</a></li>
      </ul>
    </div>
    <div class="grid_24">
      <c:if test="${message != null}">
        <p class="grid_12 ${message.type}">${message}</p>
      </c:if>
    </div>
    <div class="grid_24">
      <h1>${forum.name}</h1>
      <p>${forum.description}</p>
      <c:forEach items="${topics}" var="topic">
        <div class="span-24 fff">
          <h2><a>${topic.title}</a></h2>
          <p>${topic.text}</p>
          <p>Posted at: ${topic.createdAt}</p>
        </div>
      </c:forEach>
    </div>
    <div class="grid_24">
      <form action="<c:url value="/view/topic/create/" />" method="post" name="formtopic">
        <input type="hidden" name="id" value="${forum.id}">
        <input type="submit" value="Create new Topic" />
      </form>
    </div>
  </body>
</html>