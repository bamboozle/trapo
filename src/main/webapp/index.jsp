<%@ page 
  language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trapo Home</title>
    <script type="text/javascript">
      window.location = "<c:url value="/view/forums" />";
    </script>
  </head>
  <body>
    <noscript>
      Hey you! I can't redirect you automatically to the main page. But don't worry, just <a href="<c:url value="/view/forums" />">lets go there!</a>
    </noscript>
  </body>
</html>