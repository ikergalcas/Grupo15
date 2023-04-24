
<%@ page import="ch.qos.logback.core.net.server.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Solicitudes</title>
</head>
<body>

<h1>Solicitudes</h1>

<%
    Empleado gestor = (Empleado) request.getAttribute("gestor");
    List<Solicitud> solicitudes = (List<Solicitud>) request.getAttribute("solicitudes");
%>

<h2>Solicitudes del gestor <%=gestor.getNombre()%></h2>

<table border="1">
    <tr>
        <th>CLIENTE</th>
        <th>TIPO SOLICITUD</th>
    </tr>
    <% for (Solicitud s : solicitudes) {%>

    <tr>
        <td><%=s.getCliente().getPrimerNombre()%> <%= s.getCliente().getPrimerApellido()%></td>
        <td><%=s.getTipo_solicitud_id()%></td>
    </tr>

    <%
        }
    %>
</table>
</body>
</html>