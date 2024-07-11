<%-- 
    Document   : login
    Created on : 25 jun 2024, 23:36:45
    Author     : mario
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inicio de Sesión</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .container h1 {
            text-align: center;
        }
        .container label {
            display: block;
            margin: 10px 0 5px;
        }
        .container input {
            width: 90%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .container .submit-container {
            display: flex;
            justify-content: center;
        }
        .container input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            padding: 10px 20px;
        }
        .container input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .container a {
            text-decoration: none;
            color: #007bff;
            text-align: center;
            display: block;
            margin-top: 10px;
        }
        .container a:hover {
            color: #0056b3;
        }
        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Inicio de Sesión</h1>
        <form action="${pageContext.request.contextPath}/UsuarioControlador?action=login" method="post">
            <label>Correo:</label>
            <input type="email" name="correo" required>
            <br>
            <label>Contraseña:</label>
            <input type="password" name="contraseña" required>
            <br>
            <div class="submit-container">
                <input type="submit" value="Iniciar Sesión">
            </div>
        </form>
        <a href="${pageContext.request.contextPath}/UsuarioControlador?action=register">Registrarse</a>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </div>
</body>
</html>