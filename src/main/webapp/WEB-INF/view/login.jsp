<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.entity.Cuenta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Inicio de sesion</title>
</head>

<body>
<form action="/" method="post">
    <table>
        <tr>
            <td>NIF:</td> <td><input type="text" name="nif"></td>
        </tr>
        <tr>
            <td>Contraseña</td> <td><input type="password" name="contrasena"></td>
        </tr>
        <tr>
            <td colspan="2"> <button>Iniciar Sesión</button></td>
        </tr>
    </table>
</form>

<a href="/registroEmpresa">Registrar Empresa</a>
</body>
</html>