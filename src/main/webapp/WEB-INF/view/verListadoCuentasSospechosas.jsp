<%@ page import="es.taw.taw23.dto.CuentaSospechosa" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.CuentaCliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Cuentas sospechosas</title>
</head>
<body>

<h1>Listado de cuentas sospechosas</h1>

<%
    List<CuentaSospechosa> cuentasSospechosas = (List<CuentaSospechosa>) request.getAttribute("cuentasSospechosas");
    Integer idGestor = (Integer) request.getAttribute("idGestor");
%>

<table border="1">
    <tr>
        <th>IBAN</th>
        <th>TITULARES O AUTORIZADOS DE LA CUENTA</th>
    </tr>
    <% for (CuentaSospechosa c : cuentasSospechosas) {%>
    <tr>
        <td><%=c.getCuentaDTO().getNumeroCuenta()%></td>
        <td>
            <% for (CuentaCliente cc : c.getCuentaDTO().getCuentaCliente()) {%>
            <a href="/gestor/verCliente/<%=cc.getClienteDTO().getId()%>/<%=idGestor%>"/><%=cc.getClienteDTO().getPrimerNombre()%> <%=cc.getClienteDTO().getPrimerApellido()%></a> (<%=cc.getClienteDTO().getTipo()%>) <br/>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
<input type="submit" value="VOLVER ATRÁS" onclick="location.href='/gestor/<%=idGestor%>'">
</body>
</html>