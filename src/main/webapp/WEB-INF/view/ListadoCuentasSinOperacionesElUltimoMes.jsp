<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Cuentas sin operaciones</title>
</head>
<body>

<h1>Listado de cuentas sin actividad el último mes</h1>

<%
    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
    Integer idGestor = (Integer) request.getAttribute("idGestor");
%>

<table border="1">
    <tr>
        <th>NUMERO DE LA CUENTA</th>
        <th>FECHA DE APERTURA</th>
        <th>TIPO DE CUENTA</th>
        <th>ESTADO DE LA CUENTA</th>
    </tr>

    <%
        for (Cuenta c : cuentas) {
    %>

    <tr>
        <td><a href="/gestor/verInfoCuenta/<%=c.getId()%>"><%=c.getNumeroCuenta()%></a></td>
        <td><%=c.getFechaApertura().toLocaleString()%></td>
        <td><%=c.getTipoCuenta()%></td>
        <td><%=c.getEstadoCuenta()%></td>
    </tr>

    <% } %>
</table>

<input type="submit" value="VOLVER A LA PAGINA PRINCIPAL" onclick="location.href='/gestor/<%=idGestor%>'">
</body>
</html>