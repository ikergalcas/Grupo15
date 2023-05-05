<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Listado de transferencias a cuentas sospechosas</h1>

<%
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
    Integer idGestor = (Integer) request.getAttribute("idGestor");
%>

<table border="1">
    <tr>
        <th>IMPORTE ORIGEN</th>
        <th>IMPORTE DESTINO</th>
        <th>CUENTA ORIGEN</th>
        <th>CUENTA DESTINO</th>
        <th>FECHA DE LA TRANSFERENCIA</th>
    </tr>

    <%
        for (Movimiento m: movimientos) {
    %>

    <tr>
        <td><%=m.getImporteOrigen()%></td>
        <td><%=m.getImporteDestino()%></td>
        <td><a href="/gestor/verInfoCuenta/<%=m.getIdCuentaOrigen()%>"><%=m.getCuentaOrigen()%></a></td>
        <td><a href="/gestor/verInfoCuenta/<%=m.getIdCuentaDestino()%>"><%=m.getCuentaDestino()%></a></td>
        <th><%=m.getTimeStamp().toLocaleString()%></th>
    </tr>
    <% } %>
</table>

<input type="submit" value="VOLVER A LA PAGINA PRINCIPAL" onclick="location.href='/gestor/<%=idGestor%>'">
</body>
</html>