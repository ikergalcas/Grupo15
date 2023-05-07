<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    List<Movimiento> movimientoList = (List<Movimiento>) request.getAttribute("movimientos");
    Integer id = (Integer) request.getAttribute("id");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>

<html>
<head>
    <title>Movimientos</title>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<h1>Movimientos de la cuenta <%= cuenta.getNumeroCuenta() %></h1>

<table class="table">
    <tr>
        <th>Cuenta origen</th>
        <th>Cuenta destino</th>
        <th>Importe origen</th>
        <th>Importe destino</th>
        <th>Tipo de movimiento</th>
        <th>Fecha y hora</th>
        <th>Divisa origen</th>
        <th>Divisa destino</th>
    </tr>
    <%  if(movimientoList.size() > 0) {
            for(Movimiento mov : movimientoList) {
    %>
    <tr>
        <td><%= mov.getCuentaOrigen() %></td>
        <td><%= mov.getCuentaDestino() %></td>
        <td><%= mov.getImporteOrigen() %></td>
        <td><%= mov.getImporteDestino() %></td>
        <td><%= mov.getTipo() %></td>
        <td><%= mov.getTimeStamp() %></td>
        <td><%= mov.getDivisaCuentaOrigen() %></td>
        <td><%= mov.getDivisaCuentaDestino() %></td>
    </tr>
    <%
            }
        } else {
    %>
        <tr>
            <td>----</td>
            <td>----</td>
            <td>----</td>
            <td>----</td>
            <td>----</td>
            <td>----</td>
            <td>----</td>
            <td>----</td>
        </tr>
    <%
        }
    %>
</table>

<form style="margin-left: 30px" action="/empresa/vistaEmpresa">
    <input type="hidden" name="idEmpresa" value="<%= id %>">
    <button class="btn btn-secondary">Volver</button>
</form>

</body>
</html>