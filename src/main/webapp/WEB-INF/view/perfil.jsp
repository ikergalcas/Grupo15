<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Mi perfil</title>
</head>

<body>

<h1>Mis datos</h1>
<form:form action="/empresa/guardarPerfil" modelAttribute="asociadoEditado" method="post">
    <form:hidden path="id"/>
    NIF: <form:input path="NIF" size="9" maxlength="10"/><br/>
    Primer nombre: <form:input path="primernombre" size="30" maxlength="30"/><br/>
    Segundo nombre: <form:input path="segundonombre" size="30" maxlength="30"/><br/>
    Primer apellido: <form:input path="primerapellido" size="30" maxlength="30"/><br/>
    Segundo apellido: <form:input path="segundoapellido" size="30" maxlength="30"/><br/>
    Fecha de Nacimiento: <form:input type="date" path="fechanacimiento"/><br/>
    Calle: <form:input path="calle" /><br/>
    Numero: <form:input path="numero" /><br/>
    Puerta: <form:input path="puerta" /><br/>
    Ciudad: <form:input path="ciudad" /><br/>
    Pais: <form:input path="pais" /><br/>
    Region: <form:input path="region" /><br/>
    Codigo postal <form:input path="CP" /><br/>
    Contraseña: <form:input path="contrasena" /><br/>
    <form:button>Guardar Cambios</form:button>
</form:form>
</body>
</html>