<%@ page import="ch.qos.logback.core.net.server.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.entity.EmpleadoEntity" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Carla Serracant Guevara
--%>
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
        <th><input type="submit" value="SOLICITUDES PENDIENTES" onclick="location.href='/gestor/solicitudes/<%=gestor.getId()%>'"></th>
    </tr>
    <tr>
        <th><input type="submit" value="SOLICITUDES RESUELTAS" onclick="location.href='/gestor/solicitudesResueltas/<%=gestor.getId()%>'"></th>
    </tr>
    <tr>
        <th><input type="submit" value="SOLICITUDES RECHAZADAS" onclick="location.href='/gestor/solicitudesRechazadas/<%=gestor.getId()%>'"></th>
    </tr>
    <tr>
        <th><input type="submit" value="LISTADO DE CLIENTES" onclick="location.href='/gestor/listadoDeClientes/<%=gestor.getId()%>'"></th>
    </tr>
    <tr>
        <th><input type="submit" value="LISTADO DE EMPRESAS" onclick="location.href='/gestor/listadoDeEmpresas/<%=gestor.getId()%>'"></th>
    </tr>

    <tr>
        <th><a href="/gestor/listadoCuentasSospechosas/<%=gestor.getId()%>"> Listado de cuentas sospechosas</a></th>
    </tr>
    <tr>
        <th><a href="/gestor/listadoDeTransferenciasACuentasSospechosas/<%=gestor.getId()%>"> Listado de movimientos a cuentas sospechosas</a></th>
    </tr>
    <tr>
        <th><a href="/gestor/listadoCuentasSinOperacionesElUltimoMes/<%=gestor.getId()%>">Listado de cuentas sin actividad</a></th>
    </tr>
</table>
</body>
</html>