<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.CuentaCliente" %>
<%@ page import="es.taw.taw23.dto.CuentaSospechosa" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Información de cuenta</title>
</head>
<body>

<h1>Información de cuenta</h1>
<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    List<CuentaSospechosa> cuentasSospechosas = (List<CuentaSospechosa>) request.getAttribute("cuentasSospechosas");
%>

<table border="1">
    <tr>
        <th>NUMERO DE LA CUENTA</th>
        <th>FECHA DE APERTURA</th>
        <th>TITULAR DE LA CUENTA</th>
        <th>DINERO</th>
    </tr>

    <tr>
        <td><%=cuenta.getNumeroCuenta()%></td>
        <td><%=cuenta.getFechaApertura().toLocaleString()%></td>
        <td>
            <% for (CuentaCliente cuentaCliente : cuenta.getCuentaCliente()) {%>
                <%=cuentaCliente.getClienteDTO().getPrimerNombre()%> <%=cuentaCliente.getClienteDTO().getPrimerApellido()%> (<%=cuentaCliente.getClienteDTO().getTipo() %>) <br/>
            <% } %>
        </td>
        <td><%=cuenta.getDinero()%></td>
    </tr>
</table>

<%
    boolean sospechosa = false;
    for (CuentaSospechosa cs : cuentasSospechosas) {
        if (cs.getCuenta_id() == cuenta.getId()) {
            sospechosa = true;
%>
            Esta es una cuenta sospechosa. <br/>
            <input type="submit" value="QUITAR DE LISTAS DE CUENTAS SOSPECHOSAS" onclick="location.href='/gestor/quitarDeCuentasSospechosas/<%=cuenta.getId()%>'">
<%      }
    }

    if (!sospechosa) {%>

    <input type="submit" value="MARCAR COMO CUENTA SOSPECHOSA" onclick="location.href='/gestor/anadirACuentasSospechosas/<%=cuenta.getId()%>'">

<% } %>

<% if (!cuenta.getEstadoCuenta().equals("inactiva") && !cuenta.getEstadoCuenta().equals("bloqueada")) {%>
    <input type="submit" value="DESACTIVAR CUENTA" onclick="location.href='/gestor/desactivarCuenta/<%=cuenta.getId()%>'">
    <input type="submit" value="BLOQUEAR CUENTA" onclick="location.href='/gestor/bloquearCuenta/<%=cuenta.getId()%>'">
<% } else if (cuenta.getEstadoCuenta().equals("inactiva")) {%>
    Cuenta inactiva. <br/>
    <input type="submit" value="ACTIVAR CUENTA" onclick="location.href='/gestor/activarCuentaSinSolicitud/<%=cuenta.getId()%>'">
<% } else if (cuenta.getEstadoCuenta().equals("bloqueada")) {%>
    Cuenta bloqueada. <br/>
    <input type="submit" value="DESBLOQUEAR CUENTA" onclick="location.href='/gestor/desbloquearCuentaSinSolicitud/<%=cuenta.getId()%>'">
<% }%>
<br/>

</body>
</html>