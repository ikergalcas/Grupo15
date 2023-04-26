<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Cliente> listaAsociados = (List<Cliente>) request.getAttribute("asociados");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
%>
<html>
<head>
    <title>Title</title>
</head>
<h1>Bienvenido <%= cliente.getPrimerNombre() %></h1>

<p>Estas son tus cuentas:</p>

<table border="1">
    <tr>
        <td>Numero de Cuenta</td>
        <td>Dinero</td>
        <td>Fecha de apertura</td>
        <td>Estado</td>
        <td>Tipo</td>
        <td>Divisa</td>
        <td></td>
    </tr>
    <%
        for(Cuenta cuenta : cuentas) {
    %>
    <tr>
        <td><%= cuenta.getNumeroCuenta() %></td>
        <td><%= cuenta.getDinero() %></td>
        <td><%= cuenta.getFechaApertura() %></td>
        <td><%= cuenta.getEstadoCuenta() %></td>
        <td><%= cuenta.getTipoCuenta() %></td>
        <td><%= cuenta.getMoneda() %></td>
        <td>
            <form:form method="post" action="/empresa/cambioDivisa" modelAttribute="cambioDivisa">
                <form:hidden path="idCliente" value="<%= cliente.getId() %>" />
                <form:hidden path="cuenta" value="<%= cuenta.getNumeroCuenta() %>" />
                <form:select path="divisa">
                    <form:options items="${divisas}" itemLabel="moneda" itemValue="moneda" />
                </form:select>
                <form:button>Cambiar la divisa</form:button>
            </form:form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<p>------------------------------------------------------------------------------------------------------------------------</p>

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
        <th>NIF</th>
        <th>Nombre</th>
        <th>Empresa</th>
        <th>Puesto</th>
    </tr>
<%
    for (Cliente asociado : listaAsociados) {
%>
    <tr>
        <td><%= asociado.getNif() %></td>
        <td><%= asociado.getPrimerNombre() %></td>
        <td><%= asociado.getEmpresa() %></td>
        <td><%= asociado.getTipo() %></td>
    </tr>
<%
    }
%>

</table>

<a href="/empresa/miPerfil?id=<%= cliente.getId() %>" >Modificar mis datos</a> <br/>
<a href="/empresa/editarEmpresa?id=<%= cliente.getId()%>">Modificar datos de la empresa</a> <br/>
<a href="/empresa/transferencia?id=<%= cliente.getId() %>">Realizar transferencia</a> <br/>
<%
    if(cliente.getTipo().equals("socio")) {
%>
    <a href="/empresa/miEmpresa?id=<%= cliente.getId() %>">Ver listado de socios/autorizados de mi empresa</a>
<%
    }
%>
<a href="/empresa/transferencia?id=<%= cliente.getId() %>">Realizar transferencia</a> <br/>

</body>
</html>