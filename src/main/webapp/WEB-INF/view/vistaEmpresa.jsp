<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    Empresa empresa = (Empresa) request.getAttribute("empresa");
    List<Cliente> asociados = (List<Cliente>) request.getAttribute("asociados");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuentaEmpresa");
%>

<html>
<head>
    <title>Empresa</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <h1>Ha iniciado sesion como la empresa: <%= empresa.getNombre() %></h1>
    <div class="row">
        <div class="col-5">
            <table class="table">
                <tr>
                    <th style="text-align: center">Dar de alta a socio/autorizado</th>
                </tr>
                <tr>
                    <td>
                        <form:form action="/empresa/darDeAlta" method="post" modelAttribute="asociado">
                            <form:hidden path="id"/>
                            <form:hidden path="idEmpresa" value="<%= empresa.getIdEmpresa() %>" />
                            <form:hidden cssStyle="margin-bottom: 10px" path="acceso" value="<%=1%>" />
                            NIF*: <form:input path="nif" size="9" maxlength="9" required="true" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Primer nombre: <form:input path="primerNombre" size="30" maxlength="30" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Segundo nombre: <form:input path="segundoNombre" size="30" maxlength="30" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Primer apellido: <form:input path="primerApellido" size="30" maxlength="30" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Segundo apellido: <form:input path="segundoApellido" size="30" maxlength="30" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Fecha de Nacimiento*: <form:input type="date" path="fechaNacimiento" required="true"  cssStyle="margin-bottom: 10px"/><br/>
                            Calle: <form:input path="calle" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Numero: <form:input path="numero" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Puerta: <form:input path="puerta" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Ciudad: <form:input path="ciudad" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Pais: <form:input path="pais" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Region: <form:input path="region" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Código postal: <form:input path="cp" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Contraseña*: <form:input path="contrasena" required="true" cssStyle="width: 150px; margin-bottom: 10px" /><br/>
                            Tipo:
                            <form:select cssStyle="margin-bottom: 10px" path="tipo">
                                <form:option value="socio" label="socio" />
                                <form:option value="autorizado" label="autorizado" />
                            </form:select> <br/>
                            <form action="/empresa/darDeAlta" method="post">
                                <input type="hidden" name="idCuenta" value="<%= cuenta.getId() %>">
                                <button class="btn btn-dark">Dar de alta</button>
                            </form>
                        </form:form>
                    </td>
                </tr>
            </table>
        </div>
        <div class="col-5">
            <div class="card">
                <div class="card-header">
                    Datos de la cuenta <br/>
                    <form action="/empresa/misMovimientos" method="get">
                        <input type="hidden" name="id" value="<%= empresa.getIdEmpresa() %>" />
                        <input type="hidden" name="idCuenta" value="<%= cuenta.getId() %>" />
                        <button class="btn btn-secondary ml-1">Ver movimientos</button>
                    </form>
                </div>
                <div class="card-body">
                    <h3 class="card-title">IBAN: <%= cuenta.getNumeroCuenta() %></h3>
                    <p class="card-text">Dinero: <%= cuenta.getDinero() %></p>
                    <p class="card-text">Divisa: <%= cuenta.getMoneda() %></p>
                    <p class="card-text">Estado: <%= cuenta.getEstadoCuenta() %></p>
                </div>
            </div>
        </div>
        <div class="col-2"></div>
    </div>
    <div class="row">
        <div class="col">
            <p><strong>Asociados de la empresa:</strong></p>

            <table class="table">
                <tr>
                    <th>NIF</th>
                    <th>Primer nombre</th>
                    <th>Primer apellido</th>
                    <th>Pais</th>
                    <th>Fecha de nacimiento</th>
                    <th>Puesto</th>
                    <th>Acceso</th>
                </tr>
                <%
                    for(Cliente asociado : asociados) {
                %>
                <tr>
                    <td><%= asociado.getNif() %></td>
                    <td>
                        <%
                            String nombre = "";
                            if(asociado.getPrimerNombre() != null) {
                                nombre = asociado.getPrimerNombre();
                            }
                        %>
                        <%= nombre %>
                    </td>
                    <td>
                        <%
                            String apellido = "";
                            if(asociado.getPrimerApellido() != null) {
                                apellido = asociado.getPrimerApellido();
                            }
                        %>
                        <%= apellido %>
                    </td>
                    <td>
                        <%
                            String pais = "";
                            if(asociado.getPais() != null) {
                                pais = asociado.getPais();
                            }
                        %>
                        <%= pais %>
                    </td>
                    <td>
                        <%
                            Date fecha;
                            if(asociado.getFechaNacimiento() != null) {
                        %>
                        <%= asociado.getFechaNacimiento() %>
                        <%
                        } else {
                        %>
                        <p></p>
                        <%
                            }
                        %>
                    </td>
                    <td><%= asociado.getTipo() %></td>
                    <td>
                        <%
                            if(asociado.getAcceso() == 0) {
                        %>
                        <p>Denegado</p>
                        <%
                        } else {
                        %>
                        <p>Permitido</p>
                        <%
                            }
                        %>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>
</body>
</html>