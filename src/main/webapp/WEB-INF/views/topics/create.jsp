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
    <title>Creating new post in forum ${forum.name}</title>
  </head>
  <body>
    <div class="grid_24 topic_form">
      <form:form id="forms" action="/trapo/view/topic/save" method="post" commandName="topic" modelAttribute="topic">
        <input type="hidden" name="forum.id" value="${forum.id}" />
        <h2>Creating a new Topic in forum ${forum.name}</h2>
        <label for="title">Title
        <form:errors path="title" cssClass="fieldError" />
        </label>
        <form:input path="title" id="title"/>
        <label for="text">Text
        <form:errors path="text" cssClass="fieldError" />
        </label>
        <form:textarea path="text" id="description"/>
        <div id="actions">          
          <input type="submit" value="Post topic" />
          <a class="cancel" id="cancelaction">Cancel</a>
        </div>
      </form:form>
    </div>
  </body>
</html>