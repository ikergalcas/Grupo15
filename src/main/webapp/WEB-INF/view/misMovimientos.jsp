<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Movimiento> movimientoList = (List<Movimiento>) request.getAttribute("movimientos");
    Integer idAsociado = (Integer) request.getAttribute("idAsociado");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>

<html>
<head>
    <title>Movimientos</title>
</head>

<body>

<h1>Movimientos de la cuenta <%= cuenta.getNumeroCuenta() %></h1>

<table border="1">
    <tr>
        <th>Cuenta destino</th>
        <th>Importe</th>
        <th>Tipo de movimiento</th>
        <th>Fecha y hora</th>
    </tr>
    <%  if(movimientoList.size() > 0) {
            for(Movimiento mov : movimientoList) {
    %>
    <tr>
        <td>
            <%
                if(!mov.getCuentaDestino().equals(mov.getCuentaOrigen())) {
            %>
                <p><%= mov.getCuentaDestino() %></p>
            <%
                } else {
            %>
                <p>----</p>
            <%
                }
            %>
        </td>
        <td><%= mov.getImporteOrigen() %></td>
        <td><%= mov.getTipo() %></td>
        <td><%= mov.getTimeStamp() %></td>
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
        </tr>
    <%
        }
    %>
</table>

<a href="/empresa/?id=<%= idAsociado %>">Volver</a>

</body>
</html>