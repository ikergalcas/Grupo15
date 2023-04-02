<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.entity.Cuenta" %>
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
<table border="1">
    <tr>
        <th>Nombre</th>
        <th>Estado Cuenta</th>
        <th></th>
    </tr>
    <%
        for (Cliente asociado : listaAsociados) {
    %>
    <tr>
        <td><%= asociado.getPrimerNombre() %></td>
        <td>
            <%
                String estado;
                if(asociado.getCuentasById().get(0).getTipocuentaByTipoCuentaId().getTipo().equals("empresa")) {
                    estado = asociado.getCuentasById().get(0).getEstadocuentaByEstadoCuentaId().getEstadoCuenta();
                } else {
                    estado = asociado.getCuentasById().get(1).getEstadocuentaByEstadoCuentaId().getEstadoCuenta();
                }
            %>
            <%= estado%>
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