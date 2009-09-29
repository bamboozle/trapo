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
    <title>${forum.name} Forum - Trapo</title>
  </head>
  <body>
    <div class="span-11 last">
      <ul id="forums_menu">
        <li><a href="<c:url value="/" />">Home</a></li>
        <li><a href="<c:url value="/view/forum/create" />">Create a new Forum</a></li>
      </ul>
    </div>
    <div class="span-24">
      <c:if test="${message != null}">
        <p class="${message.type}">${message}</p>
      </c:if>
    </div>
    <div class="span-24">
      <table cellpadding="0" cellspacing="0">
        <tr>
          <th colspan="2"><h1>${forum.name}</h1></th>
        </tr>
        <tr>
          <td>Forum Name:</td>
          <td>${forum.name}</td>
        </tr>
        <tr>
          <td>Description:</td>
          <td>${forum.description}</td>
        </tr>
        <tr>
          <td>Created At:</td>
          <td>${forum.createdAt}</td>
        </tr>
        <tr>
          <td>Open:</td>
          <td>${forum.open}</td>
        </tr>
      </table>
    </div>
    <div class="span-24">
      <form id="sform" action="" method="post">
        <input name="id" type="hidden" value="${forum.id}">
      </form>
      <a class="action" href="javascript:askedit('sform', '<c:url value="/view/forum/edit/" />');">Edit</a>
      <a class="action" href="javascript:askdeletion('sform', 'Are you sure you want to delete the forum?', '<c:url value="/view/forum/delete/" />');">Delete</a>
    </div>
  </body>
</html>