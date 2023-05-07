<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
    Autores:
    Rocío Gómez Mancebo: 20%
    Pablo Alarcón Carrión: 20%
    Iker Gálvez Castillo: 20%
    Carla Serracant Guevara: 20%
    Álvaro Yuste Moreno: 20%
--%>

<html>
<head>
    <title>Inicio de sesion</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <br>
            <h1>Inicio de sesion</h1>
            <br>
            <form action="/" method="post">
                <table>
                    <tr>
                        <td style="padding-right: 8px">NIF/No Empleado:</td> <td><input type="text" name="nif"></td> <%------------- Añadir Nº empleado -------------%>
                    </tr>
                    <tr>
                        <td>Contraseña</td> <td><input type="password" name="contrasena"></td>
                    </tr>
                </table>
                <br>
                <button class="btn btn-primary">Iniciar Sesión</button>
            </form>
            <br>
            <h2>¿No tienes usuario? Registrate</h2>
            <br>
            <form action="/registroEmpresa" method="get">
                <button class="btn btn-info">Registrar Empresa</button>
            </form>
            <form action="/registroCliente" method="get">
                <button class="btn btn-info">Registrar Cliente</button>
            </form>
        </div>
    </div>
</div>\
</body>
</html>