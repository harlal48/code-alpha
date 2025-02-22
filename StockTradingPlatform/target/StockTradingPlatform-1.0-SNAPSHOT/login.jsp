<%-- 
    Document   : login
    Created on : Feb 11, 2025, 1:16:29 PM
    Author     : ACER
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 100px; }
        form { display: inline-block; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }
        input { margin: 5px; padding: 8px; width: 200px; }
        button { padding: 8px 15px; background-color: blue; color: white; border: none; cursor: pointer; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Login</h2>
    
    <% if(request.getParameter("error") != null) { %>
        <p class="error">Invalid username or password!</p>
    <% } %>

    <form action="login" method="post">
        <input type="text" name="username" placeholder="Username" required><br>
        <input type="password" name="password" placeholder="Password" required><br>
        <button type="submit">Login</button>
    </form>
</body>
</html>

