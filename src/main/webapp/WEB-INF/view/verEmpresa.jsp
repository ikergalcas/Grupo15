<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ver empresa</title>
</head>
<body>
    <%
        Empresa empresa = (Empresa) request.getAttribute("empresa");
        List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
    %>

<h1>Empresa: <%=empresa.getNombre()%></h1>

<h3>Socios y autorizados: </h3>
    <ul>
        <% for (Cliente c : empresa.getListaClientes()) {%>

            <li type="circle"><%=c.getPrimerNombre()%> <%=c.getPrimerApellido()%> (<%=c.getRol()%>)</li>

        <% } %>
    </ul>

<h3>Movimientos de la empresa</h3>

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
            <% if (m.getTipo().equals("cambioDivisa")) {%>
            Cambio de divisa
            <% } else if (m.getTipo().equals("pago")) {%>
            Transferencia bancaria
            <% } else if (m.getTipo().equals("sacarDinero")) {%>
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
            <%=m.getCuentaDestino()%>
        </td>
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
        <% }%>
    </tr>
    <% } %>
</table>

</body>
</html>