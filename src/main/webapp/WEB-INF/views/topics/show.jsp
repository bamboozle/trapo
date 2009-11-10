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
    <title>${topic.title} - Trapo</title>
  </head>
  <body>
    <div class="grid_24">
      <c:if test="${message != null}">
        <p class="grid_12 ${message.type}">${message}</p>
      </c:if>
    </div>
    <div class="grid_24">
      <h1>${forum.name}</h1>
      <h2>${topic.title}</h2>
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