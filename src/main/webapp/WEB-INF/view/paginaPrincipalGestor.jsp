<%@ page import="ch.qos.logback.core.net.server.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.entity.EmpleadoEntity" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Principal Gestor</title>
</head>
<body>

<%
    Empleado gestor = (Empleado) request.getAttribute("gestor");
%>
<h1> Página principal Gestor </h1>
<h2>Bienvenido, <%= gestor.getNombre()%></h2>

<table border="1">
    <tr>
        <th><input type="submit" value="SOLICITUDES" onclick="location.href='/gestor/solicitudes/?id=<%=gestor.getId()%>'"></th>
    </tr>
    <tr>
        <th>LISTADO DE CLIENTES</th>
    </tr>
</table>
</body>
</html>