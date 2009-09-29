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
    <script type="text/javascript" language="javascript">
      function areyousure(message) {
        if(confirm(message)) {
          return history.back(-1);
        }
      }
    </script>
  </head>
  <body>
      <c:choose>
        <c:when test="${updating}">
          <c:set var="action" value="/view/forum/update/" />
        </c:when>
        <c:otherwise>
          <c:set var="action" value="/view/forum/save/" />
        </c:otherwise>
      </c:choose>
        <div id="forms" class="forum_form">
          <form action="<c:url value="${action}" />" method="post">
          <c:if test="${updating}">
          <input type="hidden" name="id" value="${forum.id}" />
          </c:if>
          <h1>New Forum</h1>
          <p>Create a new Forum</p>
          
          <label>Name
          <span class="small">What is the forum name?</span>
          </label>
          <input type="text" name="name" id="name" value="${forum.name}" />
          
          <label>Description
          <span class="small">Describe your forum</span>
          </label>
          <textarea name="description" id="description">${forum.description}</textarea>
          
          <div id="actions">          
          <c:choose>
            <c:when test="${updating}">
              <input type="submit" value="Update Forum" />
            </c:when>
            <c:otherwise>
              <input type="submit" value="Save Forum" />
            </c:otherwise>
          </c:choose>
          <a class="cancel" href="javascript:areyousure('Do you really want to cancel');">Cancel</a>
          </div>
          </form>
        </div>
  </body>
</html>