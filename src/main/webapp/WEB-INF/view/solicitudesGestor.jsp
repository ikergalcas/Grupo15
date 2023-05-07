
<%@ page import="ch.qos.logback.core.net.server.Client" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Carla Serracant Guevara
--%>
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

        <td><input type="submit" value="Información del cliente" onclick="location.href='/gestor/verCliente/<%=s.getCliente().getId()%>/<%=gestor.getId()%>'"></td>

        <% if (s.getTipo_solicitud().getTipo().equals("alta_cliente") && s.getEstado_solicitud().getId() == 1){%>
            <td><input type="submit" value="Ver solicitud cliente" onclick="location.href='/gestor/verSolicitudAltaClienteIndividual/<%=s.getId()%>/<%=gestor.getId()%>';"/>
                <input type="submit" value="Rechazar solicitud" onclick="location.href='/gestor/rechazarSolicitud/<%=s.getId()%>/<%=gestor.getId()%>'"></td>
        <% }else if (s.getTipo_solicitud().getTipo().equals("alta_empresa") && s.getEstado_solicitud().getId() == 1){ %>
            <td><input type="submit" value="Ver solicitud empresa" onclick="location.href='/gestor/verSolicitudAltaClienteEmpresa/<%=s.getId()%>/<%=gestor.getId()%>';"/>
                <input type="submit" value="Rechazar solicitud" onclick="location.href='/gestor/rechazarSolicitud/<%=s.getId()%>/<%=gestor.getId()%>'"></td>
        <% } else if (s.getTipo_solicitud().getTipo().equals("activa_individual") && s.getEstado_solicitud().getId() == 1) { %>
            <td><input type="submit" value="Activar cuenta cliente" onclick="location.href='/gestor/activarCuentaCliente/<%=s.getId()%>/<%=gestor.getId()%>';">
                <input type="submit" value="Rechazar solicitud" onclick="location.href='/gestor/rechazarSolicitud/<%=s.getId()%>/<%=gestor.getId()%>'"></td>
        <% } else if (s.getTipo_solicitud().getTipo().equals("activa_empresa") && s.getEstado_solicitud().getId() == 1) {%>
            <td><input type="submit" value="Activar cuenta empresa" onclick="location.href='/gestor/activarCuentaEmpresa/<%=s.getId()%>/<%=gestor.getId()%>';">
                <input type="submit" value="Rechazar solicitud" onclick="location.href='/gestor/rechazarSolicitud/<%=s.getId()%>/<%=gestor.getId()%>'"></td>
        <% } else if (s.getTipo_solicitud().getTipo().equals("desbloqueo_individual") && s.getEstado_solicitud().getId() == 1) {%>
            <td><input type="submit" value="Desbloquear cuenta cliente" onclick="location.href='/gestor/desbloquearCuentaCliente/<%=s.getId()%>/<%=gestor.getId()%>';">
                <input type="submit" value="Rechazar solicitud" onclick="location.href='/gestor/rechazarSolicitud/<%=s.getId()%>/<%=gestor.getId()%>'"></td>
        <% } else if (s.getTipo_solicitud().getTipo().equals("desbloqueo_empresa") && s.getEstado_solicitud().getId() == 1) {%>
            <td><input type="submit" value="Desbloquear cuenta empresa" onclick="location.href='/gestor/desbloquearCuentaEmpresa/<%=s.getId()%>/<%=gestor.getId()%>';">
                <input type="submit" value="Rechazar solicitud" onclick="location.href='/gestor/rechazarSolicitud/<%=s.getId()%>/<%=gestor.getId()%>'"></td>
        <% } %>
    </tr>

    <%
        }
    %>
</table>
<input type="submit" value="VOLVER ATRÁS" onclick="location.href='/gestor/<%=gestor.getId()%>'">
</body>
</html>