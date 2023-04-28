<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Empresa empresa = (Empresa) request.getAttribute("empresa");
    List<Cliente> asociados = (List<Cliente>) request.getAttribute("asociados");
%>

<html>
<head>
    <title>Empresa</title>
</head>

<body>
<h1>Ha iniciado sesion como la empresa: <%= empresa.getNombre() %></h1>

<table border="1">
    <tr>
        <td style="text-align: center"><strong>Dar de alta a socio/autorizado</strong></td>
    </tr>
    <tr>
        <td>
            <form:form action="/empresa/darDeAlta" method="post" modelAttribute="asociado">
                <form:hidden path="id"/>
                <form:hidden path="empresa" value="<%= empresa.getIdEmpresa() %>" />
                <form:hidden path="acceso" value="<%=1%>" />
                NIF: <form:input path="nif" size="9" maxlength="9" /><br/>
                Primer nombre: <form:input path="primerNombre" size="30" maxlength="30" cssStyle="width: 150" /><br/>
                Segundo nombre: <form:input path="segundoNombre" size="30" maxlength="30" cssStyle="width: 150" /><br/>
                Primer apellido: <form:input path="primerApellido" size="30" maxlength="30" cssStyle="width: 150" /><br/>
                Segundo apellido: <form:input path="segundoApellido" size="30" maxlength="30" cssStyle="width: 150" /><br/>
                Fecha de Nacimiento: <form:input type="date" path="fechaNacimiento" /><br/>
                Calle: <form:input path="calle" /><br/>
                Numero: <form:input path="numero" /><br/>
                Puerta: <form:input path="puerta" /><br/>
                Ciudad: <form:input path="ciudad" /><br/>
                Pais: <form:input path="pais" /><br/>
                Region: <form:input path="region" /><br/>
                Código postal: <form:input path="cp" /><br/>
                Contraseña: <form:input path="contrasena" /><br/>
                Tipo:
                <form:select path="tipo">
                    <form:option value="socio" label="socio" />
                    <form:option value="autorizado" label="autorizado" />
                </form:select> <br/>
                <form:button>Dar de alta</form:button>
            </form:form>
        </td>
    </tr>
</table>

<p><strong>Asociados de la empresa:</strong></p>

<table border="1">
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

</body>
</html>