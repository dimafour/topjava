<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form method="post">
    <select name="profile">
        <option value="admin" selected>Admin (1)</option>
        <option value="user">User (2)</option>
    </select>
    <button type="submit">Apply</button>
</form>
</body>
</html>