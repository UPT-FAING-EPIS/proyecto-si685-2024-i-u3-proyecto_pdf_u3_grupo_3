<%-- 
    Document   : operaciones
    Created on : 26 jun 2024, 3:59:49
    Author     : mario
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operaciones PDF</title>
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
            padding: 80px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 250px;
            text-align: center;
        }
        .container h1 {
            margin-bottom: 20px;
        }
        .container p {
            margin: 10px 0;
        }
        .container ul {
            list-style: none;
            padding: 0;
        }
        .container li {
            margin: 10px 0;
        }
        .container a {
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            padding: 10px 20px;
            border: 1px solid #007bff;
            border-radius: 5px;
            display: inline-block;
            width: 90%;
        }
        .container a:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .error {
            color: red;
            text-align: center;
        }
        .mensaje {
            color: green;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Operaciones PDF</h1>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty mensaje}">
            <p class="mensaje">${mensaje}</p>
        </c:if>
        <ul>
            <li><a href="${pageContext.request.contextPath}/OperacionControlador?action=fusionarPDF">Fusionar PDF</a></li>
            <li><a href="${pageContext.request.contextPath}/OperacionControlador?action=cortarPDF">Cortar PDF</a></li>
        </ul>
        <a href="${pageContext.request.contextPath}/UsuarioControlador?action=logout">Cerrar Sesión</a>
    </div>
</body>
</html>