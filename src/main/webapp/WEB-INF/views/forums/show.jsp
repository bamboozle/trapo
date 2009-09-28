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
    <script type="text/javascript" language="JavaScript">
      function submit_form(form_id, message, action) {
          return message != null 
                 ? delete_forum(form_id, message, action)
                 : do_forum_submit(form_id, action);
      }

      function delete_forum(form_id, message, action) {
          if(confirm(message)) {
        	  do_forum_submit(form_id, action);
          }
          return false;
      }

      function do_forum_submit(form_id, action) {

        var _form = document.getElementById(form_id);
        if(_form) {
          _form.action = action;
          _form.submit();
        }
      }
    </script>
  </head>
  <body>
    <div id="forum_show">
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
      <form id="sform" action="" method="post">
        <input name="id" type="hidden" value="${forum.id}">
      </form>
      <a href="javascript:submit_form('sform', null, '<c:url value="/view/forum/edit/" />');">Edit</a>
      <a href="javascript:submit_form('sform', 'Are you sure you want to delete the forum', '<c:url value="/view/forum/delete/" />');">Delete</a>
    </div>
  </body>
</html>