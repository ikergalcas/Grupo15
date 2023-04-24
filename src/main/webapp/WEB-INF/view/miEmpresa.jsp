<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.ui.FiltroEmpresa" %>
<%@ page import="es.taw.taw23.entity.CuentaClienteEntity" %>
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

<form:form action="/empresa/filtrarMiEmpresa" modelAttribute="filtro" method="post"><br/>
    Buscar por: <br/>
    NIF: <form:input path="nif" /><br/>
    Primer Nombre: <form:input path="primerNombre" /><br/>
    Primer Apellido: <form:input path="primerApellido" /><br/>
    <form:button>Buscar</form:button>
</form:form>

<table border="1">
    <tr>
        <th>NIF</th>
        <th>Primer Nombre</th>
        <th>Primer Apellido</th>
        <th>Puesto</th>
        <th>Cuentas</th>
    </tr>
    <%
        String nombre, apellido;
        for (Cliente asociado : listaAsociados) {
    %>
    <tr>
        <td><%= asociado.getNif() %></td>
        <td>
            <%
                if(asociado.getPrimerNombre() == null) {
                    nombre = "----";
                } else{
                    nombre = asociado.getPrimerNombre();
                }
            %>
            <%= nombre %>
        </td>
        <td>
            <%
                if(asociado.getPrimerApellido() == null) {
                    apellido = "----";
                } else {
                    apellido = asociado.getPrimerApellido();
                }
            %>
            <%= apellido %>
        </td>
        <td><%= asociado.getTipo() %></td>
        <td>
            <%
                for(CuentaClienteEntity cuentaCliente : asociado.getCuentas()) {
            %>
                <%= cuentaCliente.getCuentaByCuentaId().getNumeroCuenta() %> (<%= cuentaCliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId().getTipo() %>,
                <%= cuentaCliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId().getEstadoCuenta() %>)
                | Divisa: <%= cuentaCliente.getCuentaByCuentaId().getDivisaByDivisaId().getMoneda() %>
                <%
                    if(cuentaCliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId().getTipo().equals("individual") && cuentaCliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId().getEstadoCuenta().equals("activa")) {
                %>
                    <form method="post" action="/empresa/bloquear" style="display:inline">
                        <input type="hidden" value="<%= cliente.getId() %>" name="id">
                        <input type="hidden" value="<%= cuentaCliente.getCuentaByCuentaId().getId() %>" name="idCuenta">
                        <button>Bloquear</button>
                    </form>
                <%
                    }
                %>
                <br/>
            <%
                }
            %>
        </td>
    </tr>
    <%
        }
    %>


</table>

<a href="/empresa/?id=<%= cliente.getId() %>">Volver al listado de empresas</a>
</body>
</html>