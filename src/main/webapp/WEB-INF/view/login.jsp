<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Inicio de sesion</title>
</head>

<body>
<form action="/" method="post">
    <table>
        <tr>
            <td>NIF/No Empleado:</td> <td><input type="text" name="nif"></td> <%------------- Añadir Nº empleado -------------%>
        </tr>
        <tr>
            <td>Contraseña</td> <td><input type="password" name="contrasena"></td>
        </tr>
        <tr>
            <td colspan="2"> <button>Iniciar Sesión</button></td>
        </tr>
    </table>
</form>

<a href="/registroEmpresa">Registrar Empresa</a> <br/>
<a href="/registroCliente">Registrar Cliente</a>
</body>
</html>