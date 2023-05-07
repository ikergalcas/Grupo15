<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.ui.FiltroEmpresa" %>
<%@ page import="es.taw.taw23.entity.CuentaClienteEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<Cliente> listaAsociados = (List<Cliente>) request.getAttribute("asociados");
%>
<html>

<style>

    /* Estilo base para el select */
    select {
        padding: 8px;
        border-radius: 4px;
        border: 1px solid #ccc;
        font-size: 16px;
    }

    .btn2:hover {
        background-color: #00e0ac;
    }

</style>

<head>
    <title>Mi Empresa</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <div class="row">
        <div class="col">
            <h1 style="text-align: center">Listado de socios y autorizados de mi empresa</h1>
            <%
                if(cliente.getTipo().equals("socio")) {
            %>
            <div class="container">
                <div class="row">
                    <div class="col mt-5">
                        <form:form action="/empresa/filtrarMiEmpresa" modelAttribute="filtro" method="post">
                            Buscar por NIF: <form:input path="nif" />
                            <form action="/empresa/filtrarMiEmpresa" method="post">
                                <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>">
                                <button class="btn btn-dark">Buscar</button>
                            </form>
                        </form:form>
                    </div>
                    <div class="col">
                        <form:form action="/empresa/filtrarMiEmpresa" modelAttribute="filtro" method="post">
                            Filtrar por: <br/>
                            Primer Nombre: <form:input cssStyle="margin-bottom: 10px" path="primerNombre" /><br/>
                            Primer Apellido: <form:input cssStyle="margin-bottom: 10px" path="primerApellido" /><br/>
                            Puesto:
                            <form:select cssStyle="margin-bottom: 10px" path="puesto">
                                <form:option value="" label="" />
                                <form:option value="socio" label="socio" />
                                <form:option value="autorizado" label="autorizado" />
                            </form:select> <br/>
                            <form action="/empresa/filtrarMiEmpresa" method="post">
                                <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>">
                                <button class="btn btn-dark">Buscar</button>
                            </form>
                        </form:form>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <table class="table">
                <tr>
                    <th style="text-align: center">NIF</th>
                    <th style="text-align: center">Primer Nombre</th>
                    <th style="text-align: center">Primer Apellido</th>
                    <th style="text-align: center">Puesto</th>
                    <th style="text-align: center">Acceso</th>
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
                            <button class="btn btn-danger">Bloquear</button>
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
                            <button class="btn btn-secondary">Desbloquear</button>
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
                            <button class="btn btn-primary" style="margin-top: 20px">Ver movimientos</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>


            </table>

            <form style="margin-left: 50px" action="/empresa/" method="get">
                <input type="hidden" name="id" value="<%= cliente.getId() %>">
                <button class="btn btn2" style="border: 2px solid #00e0ac; width: 300px" type="submit">Volver al listado de empresas</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>