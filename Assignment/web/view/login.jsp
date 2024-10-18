<%-- 
    Document   : login
    Created on : Oct 18, 2024, 2:44:20 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <style>
        .login-form {
            padding: 20px;
            border-radius: 2px;
            width: 300px;
            margin: 50px;
            border: solid 1px;
        }

        .login-form h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .login-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .login-form input[type="text"],
        .login-form input[type="password"] {
            width: 90%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .login-form input[type="submit"] {
            width: 97%;
            padding: 10px;
            justify-items: center;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .login-form input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <form action="login" method="POST" class="login-form">
        <h2>Login</h2>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"/>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/>
        
        <input type="submit" value="Login"/>
    </form>

</body>
</html>
