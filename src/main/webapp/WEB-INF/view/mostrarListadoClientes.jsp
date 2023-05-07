<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="ch.qos.logback.core.net.server.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Listado de clientes</title>
</head>
<body>


<h1>Listado de clientes individuales</h1>

<%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    Integer idGestor = (Integer) request.getAttribute("idGestor");
%>

<form:form method="post" action="/gestor/listadoDeClientes/buscarPorNif" modelAttribute="filtro">
    Introduzca el nif:
    <form:input path="nif"></form:input>
    <form:hidden path="idGestor" value="<%=idGestor%>"></form:hidden>
    <form:button>Filtrar</form:button>
</form:form>
<table border="1">
    <tr>
        <th>NIF</th>
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>FECHA DE NACIMIENTO</th>
        <th>CALLE</th>
        <th>CIUDAD</th>
    </tr>

    <%
        for (Cliente c : clientes) {
    %>

    <tr>
        <td><%=c.getNif()%></td>
        <td><%=c.getPrimerNombre()%> <% if (c.getSegundoNombre() != null) { %> <%=c.getSegundoNombre()%> <% } %></td>
        <td><%=c.getPrimerApellido()%> <% if (c.getSegundoApellido() != null) {%> <%=c.getSegundoApellido()%> <% } %></td>
        <td><% if (c.getFechaNacimiento() != null) {%><%=c.getFechaNacimiento().toLocaleString()%> <% } %></td>
        <td><% if(c.getCalle() != null) { %><%=c.getCalle()%> <% } %></td>
        <td><% if(c.getCiudad() != null) {%><%=c.getCiudad()%> <% } %></td>
        <td><input type="submit" value="Ver cliente" onclick="location.href='/gestor/verCliente/<%=c.getId()%>/<%=idGestor%>'"></td>
    </tr>

    <% } %>
</table>
<input type="submit" value="VOLVER ATRÁS" onclick="location.href='/gestor/<%=idGestor%>'">
</body>
</html>
