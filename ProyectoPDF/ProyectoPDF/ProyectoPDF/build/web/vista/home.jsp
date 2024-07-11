<%-- 
    Document   : home
    Created on : 25 jun 2024, 23:38:59
    Author     : mario
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
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
            padding: 50px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }
        .container h1 {
            text-align: center;
        }
        .container p {
            margin: 10px 0;
        }
        .container .button-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container a {
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            padding: 10px 20px;
            margin: 5px 0;
            border: 1px solid #007bff;
            border-radius: 5px;
            width: 100%;
            text-align: center;
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
        <h1>Bienvenido, ${usuario.nombre}!</h1>
        <p>Correo: ${usuario.correo}</p>
        <p>Suscripción actual: ${suscripcion}</p>
        <c:if test="${suscripcion eq 'Básico'}">
            <p>Operaciones realizadas: ${detalle.operacionesRealizadas} / 5</p>
        </c:if>
        <div class="button-container">
            <a href="${pageContext.request.contextPath}/OperacionControlador?action=operaciones">Operaciones PDF</a>
            <a href="${pageContext.request.contextPath}/DetalleSuscripcionUsuarioControlador?action=comprar">Comprar Suscripción Premium</a>
            <a href="${pageContext.request.contextPath}/UsuarioControlador?action=logout">Cerrar Sesión</a>
        </div>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty mensaje}">
            <p class="mensaje">${mensaje}</p>
        </c:if>
    </div>
</body>
</html>