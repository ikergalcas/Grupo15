<%@ page import="ch.qos.logback.core.net.server.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Solicitudes resueltas</title>
</head>
<body>

<h1>Solicitudes resueltas</h1>

<%
    Empleado gestor = (Empleado) request.getAttribute("gestor");
    List<Solicitud> solicitudes = (List<Solicitud>) request.getAttribute("solicitudes");
%>

<h2>Solicitudes del gestor <%=gestor.getNombre()%></h2>

<table border="1">
    <tr>
        <th>CLIENTE</th>
        <th>NIF</th>
        <th>TIPO SOLICITUD</th>
        <th>ESTADO DE LA SOLICITUD</th>
    </tr>
    <% for (Solicitud s : solicitudes) {%>

    <tr>
        <td><%=s.getCliente().getPrimerNombre()%> <%= s.getCliente().getPrimerApellido()%></td>
        <td><%=s.getCliente().getNif()%></td>
        <td><%=s.getTipo_solicitud().getTipo()%></td>
        <td><%=s.getEstado_solicitud().getEstado()%></td>

        <td><input type="submit" value="Información del cliente"></td>

        <td><input type="submit" value="Revisar solicitud aprobada"></td>

    </tr>

    <%
        }
    %>
</table>
</body>
</html>