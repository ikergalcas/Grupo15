<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.entity.MovimientosEntity" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ver cliente</title>
</head>
<body>

<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
%>

<h1>Cliente: <%=cliente.getNif()%></h1>

<h2>
    Datos del cliente:
</h2>

    Nombre: <%=cliente.getPrimerNombre()%> <% if (cliente.getSegundoNombre() != null) { %> <%=cliente.getSegundoNombre()%> <% } %></br>
    Apellidos: <%=cliente.getPrimerApellido()%> <% if (cliente.getSegundoApellido() != null) { %> <%=cliente.getSegundoApellido()%> <% }%></br>
    <% if(cliente.getFechaNacimiento() != null) { %> Fecha de nacimiento: <%=cliente.getFechaNacimiento().toLocaleString()%> <% } %></br>
    <% if (cliente.getCalle() != null) {%> Calle: <%=cliente.getCalle()%> </br> <% } %>
    <% if (cliente.getNumero() != null) {%> Numero: <%=cliente.getNumero()%> </br> <% } %>
    <% if (cliente.getPuerta() != null) {%> Puerta: <%=cliente.getPuerta()%> </br> <% } %>
    <% if (cliente.getCiudad() != null) {%> Ciudad: <%=cliente.getCiudad()%> </br> <% } %>
    <% if (cliente.getPais() != null) {%> Pais: <%=cliente.getPais()%> </br> <% } %>
    <% if (cliente.getRegion() != null) {%> Region: <%=cliente.getRegion()%> </br> <% } %>
    <% if (cliente.getCp() != null) {%> Codigo postal: <%=cliente.getCp()%> </br> <% } %>

<table border="1">
    <tr>
        <th>MOVIMIENTOS</th>
        <th>FECHA</th>
        <th>CUENTA ORIGEN</th>
        <th>CUENTA DESTINO</th>
        <th>IMPORTE DEL ORIGEN</th>
        <th>IMPORTE DEL DESTINO</th>
    </tr>
    <% for (Movimiento m : movimientos) {%>
    <tr>
        <td>
            <% if(m.getTipo().equals("cambioDivisa")) {%>
            Cambio de divisa
            <% } else if (m.getTipo().equals("pago")) {%>
            Transferencia bancaria
            <% } else if (m.getTipo().equals("sacarDinero")){ %>
            Retirada de dinero en cajero
            <% } %>
        </td>
        <td>
            <%=m.getTimeStamp().toLocaleString()%>
        </td>
        <td>
            <a href="/gestor/verInfoCuenta/<%=m.getIdCuentaOrigen()%>"><%=m.getCuentaOrigen()%></a>
        </td>
        <td>
            <a href="/gestor/verInfoCuenta/<%=m.getIdCuentaDestino()%>"><%=m.getCuentaDestino()%></a>
        </td>
           <% if (m.getTipo().equals("sacarDinero") || m.getTipo().equals("cambioDivisa")) { %>
                <td>
                    <%=m.getImporteOrigen()%>
                </td>
            <% } else { %>
                <td>
                    <%=m.getImporteOrigen()%>
                </td>
                <td>
                    <%=m.getImporteDestino()%>
                </td>
            <% } %>
    </tr>
    <% } %>

</table>
</body>
</html>
