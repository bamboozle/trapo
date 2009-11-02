<%@ page
  session="false" 
  language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="updating" value="${forum.id != null}" />
    <c:choose>
      <c:when test="${updating}">
        <title><spring:message code="Forum.create.title.update" /> ${forum.name}</title>
        <c:set var="action" value="/trapo/view/forum/update/" />
      </c:when>
      <c:otherwise>
        <title><spring:message code="Forum.create.title.save" /></title>
        <c:set var="action" value="/trapo/view/forum/save/" />
      </c:otherwise>
    </c:choose>
    <script type="text/javascript">
      $(function() { $("#cancelaction").click(function() { window.location = '<c:url value="/view/forums" />'}); });
    </script>
  </head>
  <body>
    <div class="suffix_1">
      <ul id="forums_menu">
        <li><a href="<c:url value="/view/forums" />">Forums</a></li>
        <li><a href="<c:url value="/view/forum/create" />">Create a new Forum</a></li>
      </ul>
    </div>
    <div class="grid_24 forum_form">
      <form:form id="forms" action="${action}" method="post" commandName="forum" modelAttribute="forum">
        <c:if test="${updating}">
        <input type="hidden" name="id" value="${forum.id}" />
        </c:if>
        <c:choose>
          <c:when test="${updating}">
            <h1>Updating Forum</h1>
          </c:when>
          <c:otherwise>
            <h1>New Forum</h1>
          </c:otherwise>
        </c:choose>
        <label>Name
        <span class="small">What is the forum name?</span>
        <form:errors path="name" cssClass="fieldError" />
        </label>
        <form:input path="name" id="name"/>
        <label>Description
        <span class="small">Describe your forum</span>
        <form:errors path="description" cssClass="fieldError" />
        </label>
        <form:textarea path="description" id="description"/>
        <div id="actions">          
        <c:choose>
          <c:when test="${updating}">
            <input type="submit" value="Update Forum" />
          </c:when>
          <c:otherwise>
            <input type="submit" value="Save Forum" />
          </c:otherwise>
        </c:choose>
        <a class="cancel" id="cancelaction">Cancel</a>
        </div>
      </form:form>
    </div>
  </body>
</html>