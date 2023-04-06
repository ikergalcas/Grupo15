<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.entity.Cuenta" %>
<%@ page import="es.taw.taw23.ui.FiltroEmpresa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<Cliente> listaAsociados = (List<Cliente>) request.getAttribute("asociados");
%>
<html>
<head>
    <title>Title</title>
</head>

<h1>Listado de socios y autorizados de mi empresa</h1>

<body>

<form:form action="/empresa/filtrar" modelAttribute="filtro" method="post"><br/>
    Buscar por: <br/>
    NIF: <form:input path="nif" /><br/>
    Primer Nombre: <form:input path="primerNombre" /><br/>
    Primer Apellido: <form:input path="primerApellido" /><br/>
    <form:button>Buscar</form:button>
</form:form>

<table border="1">
    <tr>
        <th>Nombre</th>
        <th>Estado Cuenta</th>
        <th>Dinero</th>
        <th></th>
    </tr>
    <%
        Boolean primero;
        for (Cliente asociado : listaAsociados) {
            primero = false;
    %>
    <tr>
        <td><%= asociado.getPrimerNombre() %></td>
        <td>
            <%
                String estado;
                if(asociado.getCuentasById().get(0).getTipocuentaByTipoCuentaId().getTipo().equals("empresa")) {
                    estado = asociado.getCuentasById().get(0).getEstadocuentaByEstadoCuentaId().getEstadoCuenta();
                    primero = true;
                } else {
                    estado = asociado.getCuentasById().get(1).getEstadocuentaByEstadoCuentaId().getEstadoCuenta();
                }
            %>
            <%= estado%>
        </td>
        <td>
            <% if(primero == true) { %>
            <%= asociado.getCuentasById().get(0).getDinero()%>
            <% } else { %>
            <%= asociado.getCuentasById().get(1).getDinero()%>
            <% } %>
        </td>
        <td>
            <form action="/empresa/bloquear" method="post">
                <input type="hidden" name="idBloq" value="<%= asociado.getId() %>" />
                <input type="hidden" name="id" value="<%= cliente.getId() %>" />
                <button type="submit">Bloquear</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>

</table>

</body>
</html>