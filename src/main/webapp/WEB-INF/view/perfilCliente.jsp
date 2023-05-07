<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
    Autor: Rocío Gómez Mancebo
--%>

<%
    Cliente cliente = (Cliente) request.getAttribute("clienteEditado");
%>

<html>
<head>
    <title>Mi perfil</title>
</head>

<body>


<h1>Mis datos</h1>
<form:form action="/cliente/guardarPerfilCliente" modelAttribute="clienteEditado" method="post">
    <form:hidden path="id"/>
    NIF: <form:input path="nif" size="9" maxlength="10" value="<%= cliente.getNif()%>"/><br/>
    Primer nombre: <form:input path="primerNombre" size="30" maxlength="30" value="<%= cliente.getPrimerNombre()%>" /><br/>
    Segundo nombre: <form:input path="segundoNombre" size="30" maxlength="30" value="<%= cliente.getSegundoNombre()%>"/><br/>
    Primer apellido: <form:input path="primerApellido" size="30" maxlength="30" value="<%= cliente.getPrimerApellido()%>"/><br/>
    Segundo apellido: <form:input path="segundoApellido" size="30" maxlength="30" value="<%= cliente.getSegundoApellido()%>"/><br/>
    Fecha de Nacimiento: <form:input type="date" path="fechaNacimiento" value="<%= cliente.getFechaNacimiento()%>"/><br/>
    Calle: <form:input path="calle" value="<%= cliente.getCalle()%>"/><br/>
    Numero: <form:input path="numero" value="<%= cliente.getNumero()%>"/><br/>
    Puerta: <form:input path="puerta" value="<%= cliente.getPuerta()%>"/><br/>
    Ciudad: <form:input path="ciudad" value="<%= cliente.getCiudad()%>"/><br/>
    Pais: <form:input path="pais" value="<%= cliente.getPais()%>"/><br/>
    Region: <form:input path="region" value="<%= cliente.getRegion()%>"/><br/>
    Código postal <form:input path="cp" value="<%= cliente.getCp()%>"/><br/>
    Contraseña: <form:input path="contrasena" value="<%= cliente.getContrasena()%>"/><br/>
    <form:button>Guardar Cambios</form:button>
</form:form>
<br>
<a href="/cliente/<%= cliente.getId() %>">Volver a pagina de movimientos</a><br/>

</body>
</html>