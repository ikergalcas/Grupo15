<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Listado empresas</title>
</head>
<body>
<h1>Listado de empresas</h1>

<%
    List<Empresa> empresas = (List<Empresa>) request.getAttribute("empresas");
%>

<table border="1">
    <tr>
        <td>NOMBRE DE LA EMPRESA</td>
        <td>AUTORIZADOS Y SOCIOS DE LA EMPRESA</td>
    </tr>

    <% for (Empresa e : empresas) {%>
    <tr>
        <td>
            <%=e.getNombre()%>
        </td>
        <td>
            <% for (Cliente c : e.getListaClientes()) {%>
            <a href="/gestor/verCliente/<%=c.getId()%>"><%=c.getPrimerNombre()%> <%=c.getPrimerApellido()%></a> <br/>
            <% } %>
        </td>
        <td>
            <input type="submit" value="Ver empresa" onclick="location.href='/gestor/verEmpresa/<%=e.getId()%>'">
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>