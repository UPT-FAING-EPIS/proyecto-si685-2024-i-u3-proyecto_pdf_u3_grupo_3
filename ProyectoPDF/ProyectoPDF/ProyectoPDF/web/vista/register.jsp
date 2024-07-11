<%-- 
    Document   : register
    Created on : 25 jun 2024, 23:38:31
    Author     : mario
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
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
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
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
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .container input[type="submit"] {
            background-color: #4ec1ff;
            color: #fff;
            border: none;
            cursor: pointer;
            
        }
        .container input[type="submit"]:hover {
            background-color: #0086db;
        }
        .error {
            color: red;
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Registro de Usuario</h1>
        <form id="registroForm" action="${pageContext.request.contextPath}/UsuarioControlador?action=register2" method="post">
            <label>Nombre:</label>
            <input type="text" id="nombre" name="nombre" required>
            <span class="error" id="nombreError">Por favor, ingrese su nombre.</span>
            <br>
            <label>Correo:</label>
            <input type="email" id="correo" name="correo" required>
            <span class="error" id="correoError">Por favor, ingrese un correo válido.</span>
            <br>
            <label>Contraseña:</label>
            <input type="password" id="contraseña" name="contraseña" required>
            <span class="error" id="contraseñaError">La contraseña debe tener al menos 6 caracteres.</span>
            <br>
            <input type="submit" value="Registrar">
        </form>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </div>
    <script>
        document.getElementById('registroForm').addEventListener('submit', function(event) {
            var valid = true;

            // Validar el campo de nombre
            var nombre = document.getElementById('nombre').value;
            if (nombre.trim() === '') {
                document.getElementById('nombreError').style.display = 'block';
                valid = false;
            } else {
                document.getElementById('nombreError').style.display = 'none';
            }

            // Validar el campo de correo
            var correo = document.getElementById('correo').value;
            var correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!correoRegex.test(correo)) {
                document.getElementById('correoError').style.display = 'block';
                valid = false;
            } else {
                document.getElementById('correoError').style.display = 'none';
            }

            // Validar el campo de contraseña
            var contraseña = document.getElementById('contraseña').value;
            if (contraseña.length < 6) {
                document.getElementById('contraseñaError').style.display = 'block';
                valid = false;
            } else {
                document.getElementById('contraseñaError').style.display = 'none';
            }

            if (!valid) {
                event.preventDefault();
            }
        });
    </script>
</body>
</html>