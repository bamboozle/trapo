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
	${forum.name} - open = ${forum.open}
	<a href="/trapo/forum/${forum.name}">show</a>
    <c:out value="${forum.name}" escapeXml="true" />
</body>
</html>