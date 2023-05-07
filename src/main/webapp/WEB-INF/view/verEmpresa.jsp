<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Ver empresa</title>
</head>
<body>

    <%
        Empresa empresa = (Empresa) request.getAttribute("empresa");
        List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
        Integer idGestor = (Integer) request.getAttribute("idGestor");
    %>

<h1>Empresa: <%=empresa.getNombre()%></h1>

<h3>Socios y autorizados: </h3>
    <ul>
        <% for (Cliente c : empresa.getListaClientes()) {%>

            <li type="circle"><%=c.getPrimerNombre()%> <%=c.getPrimerApellido()%> (<%=c.getTipo()%>)</li>

        <% } %>
    </ul>


<h3>Movimientos de la empresa</h3>

    <form:form method="post" action="/gestor/verEmpresa/Ordenado" modelAttribute="filtro">
        Tipo de movimiento:
        <form:select path="tipoMovimiento">
            <form:option value="NONE" label=" "></form:option>
            <form:options items="${tiposDeMovimientos}"></form:options>
        </form:select>
        Operaciones el último mes <form:checkbox path="fecha"></form:checkbox>
        <form:hidden path="idGestor" value="<%=idGestor%>"></form:hidden>
        <form:hidden path="idEmpresa" value="<%=empresa.getId()%>"></form:hidden>
        <form:button>Filtrar</form:button>
    </form:form>
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
            <a href="/gestor/verInfoCuenta/<%=m.getIdCuentaDestino()%>"><%=m.getCuentaDestino()%></a>
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
    <input type="submit" value="VOLVER A LA PAGINA PRINCIPAL" onclick="location.href='/gestor/<%=idGestor%>'">
</body>
</html>