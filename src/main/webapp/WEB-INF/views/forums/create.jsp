<%@ page
  session="false" 
  language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="updating" value="${forum.id != null}" />
    <c:choose>
      <c:when test="${updating}">
        <title>Updating forum ${forum.name}</title>
      </c:when>
      <c:otherwise>
        <title>Creating a new forum</title>
      </c:otherwise>
    </c:choose>
  </head>
  <body>
    <fieldset id="forum_form">
      <c:choose>
        <c:when test="${updating}">
          <legend>Updating forum ${forum.name}</legend>
        </c:when>
        <c:otherwise>
          <legend>Creating a new forum</legend>
        </c:otherwise>
      </c:choose>
      <form action="/trapo/forum/save" method="post">
        <c:if test="${updating}">
        <input type="hidden" name="id" value="${forum.id}" />
        </c:if>
        
        <label for="name">Name:</label>
        <span id="help">The forum name. This field is required.</span>
        <input type="text" name="name" id="name" value="${forum.name}" />
        <br />
        
        <label for="description">Description:</label>
        <span id="help">The forum name. This field is required.</span>
        <input type="text" name="description" id="description" value="${forum.description}" />
        <br />
        
        <c:choose>
          <c:when test="${update}">
            <input type="submit" value="Update Forum" />
          </c:when>
          <c:otherwise>
            <input type="submit" value="Save Forum" />
          </c:otherwise>
        </c:choose>
        <a class="cancel" href="javascript:;">Cancel</a>
        
      </form>
    </fieldset>
  </body>
</html>