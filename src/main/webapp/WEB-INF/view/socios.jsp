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

<h1>Listado de socios</h1>

<body>
<table>
    <tr>
        <th>Nombre</th>
    </tr>
<%
    for (Cliente socio : listaSocios) {
%>
    <tr>
        <td><%= socio.getPrimerNombre() %></td>
    </tr>
<%
    }
%>

</table>
</body>
</html>