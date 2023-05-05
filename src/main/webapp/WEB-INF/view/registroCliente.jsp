<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
    Autor: Rocío Gómez Mancebo
--%>

<html>
<head>
    <title>Registro cliente</title>
</head>

<body>

<h1>Datos</h1>
<form:form action="registroCliente" method="post" modelAttribute="cliente">
    <form:hidden path="id"/>
    NIF (obligatorio): <form:input path="nif" size="9" maxlength="10" required="true" /> <br/>
    Primer nombre: <form:input path="primerNombre" size="30" maxlength="30" /><br/>
    Segundo nombre: <form:input path="segundoNombre" size="30" maxlength="30"/><br/>
    Primer apellido: <form:input path="primerApellido" size="30" maxlength="30"/><br/>
    Segundo apellido: <form:input path="segundoApellido" size="30" maxlength="30" /><br/>
    Fecha de Nacimiento: <form:input type="date" path="fechaNacimiento" required="true" /><br/>
    Calle: <form:input path="calle" /><br/>
    Numero: <form:input path="numero"/><br/>
    Puerta: <form:input path="puerta"/><br/>
    Ciudad: <form:input path="ciudad"/><br/>
    Pais: <form:input path="pais"/><br/>
    Region: <form:input path="region" /><br/>
    Código postal <form:input path="cp" /><br/>
    Contraseña (obligatorio): <form:input path="contrasena" required="true" /><br/>
    <form:button>Crear</form:button>
</form:form>
<br>
</body>
</html>
