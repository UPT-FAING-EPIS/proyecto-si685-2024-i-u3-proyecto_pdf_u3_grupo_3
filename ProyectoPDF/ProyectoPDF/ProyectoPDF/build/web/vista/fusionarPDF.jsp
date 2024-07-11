<%-- 
    Document   : fusionarPDF
    Created on : 26 jun 2024, 3:56:44
    Author     : mario
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fusionar PDF</title>
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
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container label {
            margin-top: 10px;
        }
        .container input[type="file"] {
            margin-bottom: 15px;
        }
        .container button {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 10px;
        }
        .container button:hover {
            background-color: #0056b3;
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
        .error {
            color: red;
        }
        .mensaje {
            color: green;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Fusionar Archivos PDF</h1>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty mensaje}">
            <p class="mensaje">${mensaje}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/OperacionControlador" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="fusionarPDF" />
            <label for="archivo1">Seleccionar primer PDF:</label>
            <input type="file" id="archivo1" name="archivos" required /><br>
            <label for="archivo2">Seleccionar segundo PDF:</label>
            <input type="file" id="archivo2" name="archivos" required /><br>
            <button type="submit">Fusionar</button>
        </form>
        <c:if test="${not empty archivoFusionado}">
            <p>Archivo fusionado: <a href="${pageContext.request.contextPath}/${archivoFusionado}" download>Descargar PDF Fusionado</a></p>
        </c:if>
        <a href="${pageContext.request.contextPath}/OperacionControlador?action=operaciones">Volver a Operaciones</a>
    </div>
</body>
</html>