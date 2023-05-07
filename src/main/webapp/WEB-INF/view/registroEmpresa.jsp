<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */
%>

<html>
<head>
    <title>Registro Empresa</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col">
            <form:form method="post" action="/registroEmpresa" modelAttribute="empresa">
                <h4>Datos Empresa:</h4>
                <form:hidden path="idEmpresa" />
                Nombre: <form:input path="nombre" /> <br/>
                CIF: <form:input path="cif" /> <br/>
                Contraseña: <form:input path="contrasenaEmpresa" /> <br/>
        </div>
    </div>
</div>
<hr>
<div class="container">
    <div class="row">
        <div class="col">
                <h4>Datos socio:</h4>
                <form:form method="post" action="registroEmpresa" modelAttribute="socio">
                    <form:hidden path="id" />
                    NIF*: <form:input path="nif" /> <br/>
                    Primer Nombre: <form:input path="primerNombre" /> <br/>
                    Segundo Nombre: <form:input path="segundoNombre" /> <br/>
                    Primer Apellido: <form:input path="primerApellido" /> <br/>
                    Segundo Apellido: <form:input path="segundoApellido" /> <br/>
                    Fecha de Nacimiento*: <form:input path="fechaNacimiento" type="date" /> <br/>
                    Calle: <form:input path="calle" /> <br/>
                    Numero: <form:input path="numero" /> <br/>
                    Puerta: <form:input path="puerta" /> <br/>
                    Ciudad: <form:input path="ciudad" /> <br/>
                    Pais: <form:input path="pais" /> <br/>
                    Region: <form:input path="region" /> <br/>
                    Código Postal: <form:input path="cp" /> <br/>
                    Contraseña*: <form:input path="contrasena" /> <br/>
                    <form:hidden path="tipo" />
                    <form:hidden path="idEmpresa" />
                    <form:hidden path="acceso" />
                    <button class="btn btn-dark">Guardar</button>
                </form:form>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>