<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
 %>
<html>
<head>
    <title>Titulo</title>
</head>

<body>
<h1>Cuenta de <%=cliente.getPrimerNombre()%></h1>

<h2> Lista de MOVIMIENTOS</h2>
Filtro -- Futura implementacion
<table border="1">
    <tr>
        <th>ID</th>
        <th>Cuenta Origen</th>
        <th>Cuenta Destino</th>
        <th>Importe de Origen</th>
        <th>Modena de Origen</th>
        <th>Importe de Destino</th>
        <th>Moneda de Destino</th>
        <th>Tipo de movimiento</th>
        <th>Fecha </th>
    </tr>

    <%
        for(Movimiento m : movimientos){
    %>
    <tr>
        <td align="center"><%= m.getId()%></td>
        <td align="center"><%=m.getCuentaOrigen()%></td>
        <td align="center"><%=m.getCuentaDestino()%></td>
        <td align="center"><%=m.getImporteOrigen()%></td>
        <td align="center"><%=m.getDivisaCuentaOrigen()%></td><%--CAMBIAR EN UN FUTURO MOVIMIENTO TIPO MONEDA --%>
        <td align="center"><%=m.getImporteDestino().shortValue()%></td>
        <td align="center"><%=m.getDivisaCuentaDestino()%></td>
        <td align="center"><%=m.getTipo()%></td>
        <td align="center"><%=m.getTimeStamp()%></td>
    </tr>
    <%
        }
    %>

</table>

<br>
<table border="1">
    <tr>
        <th>Dinero Actual</th>
        <th>Divisa</th>
        <th>Cambiar Divisa</th>
    </tr>
    <tr>
        <th><%=cuenta.getDinero()%></th>
        <th><%=cuenta.getMoneda()%></th>
        <th valign="center"> <form:form method="post" action="/cliente/cambioDivisa" modelAttribute="cambioDivisa">
            <form:hidden path="idCliente" value="<%= cliente.getId() %>" />
            <form:hidden path="cuenta" value="<%= cuenta.getNumeroCuenta() %>" />
            <form:select path="divisa">
                <form:options items="${divisas}" itemLabel="moneda" itemValue="moneda" />
            </form:select>
            <form:button>Cambiar la divisa</form:button>
        </form:form></th>
    </tr>
</table>
<br>

<a href="/cliente/transferenciaCliente/<%= cliente.getId() %>">Realizar transferencia</a> <br/>
<a href="/cliente/perfilCliente/<%=cliente.getId()%>" >Modificar mis datos</a> <br/>



</body>
</html>