<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
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

<%
    if(cliente.getTipo().equals("socio")) {
%>
    <form:form action="/empresa/filtrarMiEmpresa" modelAttribute="filtro" method="post">
        Buscar por NIF: <form:input path="nif" /> <br/>
        <form action="/empresa/filtrarMiEmpresa" method="post">
            <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>">
            <button>Buscar</button>
        </form>
    </form:form>

    <form:form action="/empresa/filtrarMiEmpresa" modelAttribute="filtro" method="post"><br/>
        Filtrar por: <br/>
        Primer Nombre: <form:input path="primerNombre" /><br/>
        Primer Apellido: <form:input path="primerApellido" /><br/>
        Puesto:
        <form:select path="puesto">
            <form:option value="" label="" />
            <form:option value="socio" label="socio" />
            <form:option value="autorizado" label="autorizado" />
        </form:select> <br/>
        <form action="/empresa/filtrarMiEmpresa" method="post">
            <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>">
            <button>Buscar</button>
        </form>
    </form:form>
<%
    }
%>
<table border="1">
    <tr>
        <th>NIF</th>
        <th>Primer Nombre</th>
        <th>Primer Apellido</th>
        <th>Puesto</th>
        <th>Acceso</th>
        <th></th>
    </tr>
    <%
        String nombre, apellido;
        for (Cliente asociado : listaAsociados) {
    %>
    <tr>
        <td style="text-align: center"><%= asociado.getNif() %></td>
        <td style="text-align: center">
            <%
                if(asociado.getPrimerNombre() == null) {
                    nombre = "";
                } else{
                    nombre = asociado.getPrimerNombre();
                }
            %>
            <%= nombre %>
        </td style="text-align: center">
        <td>
            <%
                if(asociado.getPrimerApellido() == null) {
                    apellido = "";
                } else {
                    apellido = asociado.getPrimerApellido();
                }
            %>
            <%= apellido %>
        </td>
        <td style="text-align: center"><%= asociado.getTipo() %></td>
        <td style="text-align: center">
            <%
                if(asociado.getAcceso() == 1) {
            %>
            <p>
                Permitido
                <%
                    if(cliente.getTipo().equals("socio")) {
                %>
                    <form action="/empresa/bloquear" method="post">
                        <input type="hidden" value="<%= asociado.getId() %>" name="idBloqueado" />
                        <input type="hidden" value="<%= cliente.getId() %>" name="id" />
                        <button>Bloquear</button>
                    </form>
                <%
                    }
                %>
            </p>
            <%
                } else {
            %>
            <p style="text-align: center">
                Denegado
                <%
                    if(cliente.getTipo().equals("socio")) {
                %>
                    <form action="/empresa/desbloquear" method="post">
                        <input type="hidden" value="<%= asociado.getId() %>" name="idBloqueado" />
                        <input type="hidden" value="<%= cliente.getId() %>" name="id" />
                        <button>Desbloquear</button>
                    </form>
                <%
                    }
                %>
            </p>
            <%
                }
            %>
        </td>
        <td>
            <form action="/empresa/movimientosAsociado" method="get">
                <input type="hidden" value="<%= asociado.getId() %>" name="idAsociado" />
                <button>Ver movimientos</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>


</table>

<a href="/empresa/?id=<%= cliente.getId() %>">Volver al listado de empresas</a>
</body>
</html>