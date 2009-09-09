<%@ page 
  language="java" 
  contentType="text/html; charset=UTF-8" 
  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trapo</title>
  </head>
  <body>
    <div id="header">
      <ul id="menu">
        <li><a href="/manage/forums">Forums</a></li>
        <li><a href="/manage/users">Users</a></li>
        <li><a href="/manage/stats">Statistics</a></li>
        <li><a href="/manage/trapo">Configuration</a></li>
      </ul>
      <c:if test="${user != null}">
      <ul id="preferences">
        <li><a href="/user/${user.name}/profile">Profile</a></li>
        <li><a href="/user/${user.name}/bookmarks">Bookmarks</a></li>
        <li><a href="/logout">Logout</a></li>
      </ul>
      </c:if>
    </div>
    <div id="main">
      <div class="forum">
        <a href="forum/${forum.name}" class="forum_name">${forum.name}</a>
        <p class="forum_stats">${forum.topicsCount}, ${forum.postsCount}</p>
        <p class="forum_description">${forum.description}</p>
        <div class="last_post">
          <a href="users/user">user name</a>
        </div>
      </div>
    </div>
    <div id="footer">
      <p id="copyright">2009</p>
      <p id="powered"><a href="">trapo</a></p>
    </div>
  </body>
</html>