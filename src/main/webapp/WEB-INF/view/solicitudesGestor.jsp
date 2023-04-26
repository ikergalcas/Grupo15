
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
        <% if ((s.getTipo_solicitud().getTipo().equals("alta_cliente") && s.getEstado_solicitud().getId() == 1) ||
                (s.getTipo_solicitud().getTipo().equals("alta_empresa") && s.getEstado_solicitud().getId() == 1)) {%>
            <td><input type="submit" value="Ver solicitud" onclick="location.href='/gestor/verSolicitudAltaCliente/<%=s.getId()%>/<%=gestor.getId()%>';"/>  <input type="submit" value="Rechazar solicitud"></td>
        <% } else if (s.getEstado_solicitud().getId() == 3) { %>
            <td><input type="submit" value="Revisar solicitud aprobada"></td>
        <% } else if (s.getTipo_solicitud().getTipo().equals("activacion")) { %>
            <td><input type="submit" value="Activar cuenta"></td>
        <% } else if (s.getTipo_solicitud().getTipo().equals("desbloqueo")) {%>
            <td><input type="submit" value="Desbloquear cuenta"></td>
        <% } %>
    </tr>

    <%
        }
    %>
</table>
</body>
</html>