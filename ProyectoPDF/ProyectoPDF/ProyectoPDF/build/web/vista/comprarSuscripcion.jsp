<%-- 
    Document   : comprarSuscripcion
    Created on : 26 jun 2024, 1:37:54
    Author     : mario
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comprar Suscripción Premium</title>
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
            text-align: center;
        }
        .container h1 {
            margin-bottom: 20px;
        }
        .container p {
            margin: 10px 0;
        }
        .container form {
            margin-top: 20px;
        }
        .container input[type="submit"] {
            background-color: #4ec1ff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 10px;
        }
        .container input[type="submit"]:hover {
            background-color: #008fe5;
        }
        .container a {
            text-decoration: none;
            color: #007bff;
            margin-top: 20px;
            display: inline-block;
        }
        .container a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Comprar Suscripción Premium</h1>
        <p>Precio: $25.00</p>
        <p>Fecha de Inicio: <fmt:formatDate value="${fechaInicio}" pattern="yyyy-MM-dd"/></p>
        <p>Fecha de Final: <fmt:formatDate value="${fechaFinal}" pattern="yyyy-MM-dd"/></p>
        <form action="${pageContext.request.contextPath}/DetalleSuscripcionUsuarioControlador?action=comprarPremium" method="post">
            <input type="hidden" name="precio" value="25.00">
            <input type="hidden" name="fechaInicio" value="${fechaInicio}">
            <input type="hidden" name="fechaFinal" value="${fechaFinal}">
            <input type="submit" value="Confirmar Compra">
        </form>
        <a href="${pageContext.request.contextPath}/vista/home.jsp">Cancelar</a>
    </div>
</body>
</html>