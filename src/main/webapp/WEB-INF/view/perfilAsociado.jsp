<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    Cliente cliente = (Cliente) request.getAttribute("asociadoEditado");
%>

<html>

<style>
    .btn1:hover {
        background-color: #00c1ff;
    }
</style>

<head>
    <title>Mi perfil (Asociado)</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <div class="row">
        <div class="col">

            <h1>Mis datos</h1>
            <form:form action="/empresa/guardarPerfil?id=<%= cliente.getId() %>" modelAttribute="asociadoEditado" method="post">
                <form:hidden path="id"/>
                <form:hidden path="idEmpresa" />
                <form:hidden path="tipo" />
                NIF: <form:input cssStyle="margin-bottom: 10px" path="nif" size="9" maxlength="10" value="<%= cliente.getNif()%>"/><br/>
                Primer nombre: <form:input cssStyle="margin-bottom: 10px" path="primerNombre" size="30" maxlength="30" value="<%= cliente.getPrimerNombre()%>" /><br/>
                Segundo nombre: <form:input cssStyle="margin-bottom: 10px" path="segundoNombre" size="30" maxlength="30" value="<%= cliente.getSegundoNombre()%>"/><br/>
                Primer apellido: <form:input cssStyle="margin-bottom: 10px" path="primerApellido" size="30" maxlength="30" value="<%= cliente.getPrimerApellido()%>"/><br/>
                Segundo apellido: <form:input cssStyle="margin-bottom: 10px" path="segundoApellido" size="30" maxlength="30" value="<%= cliente.getSegundoApellido()%>"/><br/>
                Fecha de Nacimiento: <form:input cssStyle="margin-bottom: 10px" type="date" path="fechaNacimiento" value="<%= cliente.getFechaNacimiento()%>" required="true"/><br/>
                Calle: <form:input cssStyle="margin-bottom: 10px" path="calle" value="<%= cliente.getCalle()%>"/><br/>
                Numero: <form:input cssStyle="margin-bottom: 10px" path="numero" value="<%= cliente.getNumero()%>"/><br/>
                Puerta: <form:input cssStyle="margin-bottom: 10px" path="puerta" value="<%= cliente.getPuerta()%>"/><br/>
                Ciudad: <form:input cssStyle="margin-bottom: 10px" path="ciudad" value="<%= cliente.getCiudad()%>"/><br/>
                Pais: <form:input cssStyle="margin-bottom: 10px" path="pais" value="<%= cliente.getPais()%>"/><br/>
                Region: <form:input cssStyle="margin-bottom: 10px" path="region" value="<%= cliente.getRegion()%>"/><br/>
                Código postal <form:input cssStyle="margin-bottom: 10px" path="cp" value="<%= cliente.getCp()%>"/><br/>
                Contraseña: <form:input cssStyle="margin-bottom: 10px" path="contrasena" value="<%= cliente.getContrasena()%>"/><br/>
                <button class="btn btn-dark">Guardar Cambios</button>
            </form:form>

            <form action="/empresa/" method="get">
                <input type="hidden" name="id" value="<%= cliente.getId() %>">
                <button class="btn btn1" style="border: 2px solid #00c1ff; width: 400px" type="submit">Volver al listado de empresas</button>
            </form>
        </div>
    </div>
</div>
</body>

</html>