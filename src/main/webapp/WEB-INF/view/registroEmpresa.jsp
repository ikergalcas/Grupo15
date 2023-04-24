<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registro Empresa</title>
</head>

<body>

<form:form method="post" action="registroEmpresa" modelAttribute="empresa">
    Datos Empresa: <br/>
    <form:hidden path="idEmpresa" />
    Nombre: <form:input path="nombre" /> <br/>
    -------------------------------------------------------------- <br/>
    Datos socio: <br/>
    <form:form method="post" action="registroEmpresa" modelAttribute="socio">
        NIF (obligatorio): <form:input path="nif" /> <br/>
        Primer Nombre: <form:input path="primerNombre" /> <br/>
        Segundo Nombre: <form:input path="segundoNombre" /> <br/>
        Primer Apellido: <form:input path="primerApellido" /> <br/>
        Segundo Apellido (opcional): <form:input path="segundoApellido" /> <br/>
        Fecha de Nacimiento: <form:input path="fechaNacimiento" type="date" /> <br/>
        Calle: <form:input path="calle" /> <br/>
        Numero: <form:input path="numero" /> <br/>
        Puerta: <form:input path="puerta" /> <br/>
        Ciudad: <form:input path="ciudad" /> <br/>
        Pais: <form:input path="pais" /> <br/>
        Region: <form:input path="region" /> <br/>
        Código Postal: <form:input path="cp" /> <br/>
        Contraseña (obligatorio): <form:input path="contrasena" /> <br/>
        <form:button>Guardar</form:button>
    </form:form>
</form:form>

</body>
</html>