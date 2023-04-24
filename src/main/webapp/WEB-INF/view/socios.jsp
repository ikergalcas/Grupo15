<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Cliente> listaSocios = (List<Cliente>) request.getAttribute("asociados");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<html>
<head>
    <title>Title</title>
</head>

<h1>Listado de todos los socios y autorizados</h1>

<body>

<form:form action="/empresa/filtrar" method="post" modelAttribute="filtro">
    <form:hidden path="nif" />
    <form:hidden path="primerNombre" />
    <form:hidden path="primerApellido" />
    Buscar por nombre: <form:input path="nombreEmpresa" /> <br/>
    <form:button>Buscar</form:button> <br/>
</form:form>

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
        <td><%= socio.getEmpresa() %></td>
        <%
            if(socio.getTipo().equals("socio") && socio.getIdEmpresa() == cliente.getIdEmpresa()) {
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

<a href="/empresa/miPerfil?id=<%= cliente.getId() %>" >Modificar mis datos</a> <br/>
<a href="/empresa/editarEmpresa?id=<%= cliente.getId()%>">Modificar datos de la empresa</a> <br/>
<a href="/empresa/transferencia?id=<%= cliente.getId() %>">Realizar transferencia</a>
</body>
</html>