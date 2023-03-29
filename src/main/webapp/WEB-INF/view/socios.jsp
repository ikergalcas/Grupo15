<%@ page import="es.taw.taw23.entity.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Cliente> listaSocios = (List<Cliente>) request.getAttribute("socios");
%>
<html>
<head>
    <title>Title</title>
</head>

<h1>Listado de todos los socios y autorizados</h1>

<body>
<table border="2">
    <tr>
        <th>Nombre</th>
        <th>Empresa</th>
        <th></th>
    </tr>
<%
    for (Cliente socio : listaSocios) {
%>
    <tr>
        <td><%= socio.getPrimerNombre() %></td>
        <td><%= socio.getEmpresaByEmpresaIdEmpresa().getNombre() %></td>
        <%
            if(socio.getRolclienteByRolclienteId().getTipo().equals("socio")) {
        %>
        <td><a href="/empresa/miEmpresa?id=<%= socio.getId() %>" >Ver socios/autorizados de mi empresa</a></td>
        <%
            }
        %>
    </tr>
<%
    }
%>

</table>
</body>
</html>