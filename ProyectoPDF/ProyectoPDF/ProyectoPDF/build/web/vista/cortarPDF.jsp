<%-- 
    Document   : cortarPDF
    Created on : 26 jun 2024, 4:36:15
    Author     : mario
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cortar PDF</title>
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
        .container input[type="file"],
        .container input[type="number"] {
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
        <h1>Cortar Archivos PDF</h1>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty mensaje}">
            <p class="mensaje">${mensaje}</p>
        </c:if>
        <c:choose>
            <c:when test="${numeroPaginas == null}">
                <form action="${pageContext.request.contextPath}/OperacionControlador" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="subirPDF" />
                    <label for="archivo">Seleccionar PDF:</label>
                    <input type="file" id="archivo" name="archivo" required /><br>
                    <button type="submit">Subir y Obtener Páginas</button>
                </form>
            </c:when>
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/OperacionControlador" method="post">
                    <input type="hidden" name="action" value="cortarPDF" />
                    <p>Número de páginas: ${numeroPaginas}</p>
                    <label for="inicio">Página de inicio:</label>
                    <input type="number" id="inicio" name="inicio" min="1" max="${numeroPaginas}" required /><br>
                    <label for="fin">Página de fin:</label>
                    <input type="number" id="fin" name="fin" min="1" max="${numeroPaginas}" required /><br>
                    <button type="submit">Cortar</button>
                </form>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/OperacionControlador?action=operaciones">Volver a Operaciones</a>
    </div>
</body>
</html>