<!--Pablo Alarcón Carrión-->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<html>
<head>
    <title>Datos del cliente</title>
</head>
<body>
    <%
    Cliente cliente = (Cliente) request.getAttribute("cliente"); %>


<table>
    <td>
    <th>
        <form action="/cajero/<%=cliente.getId()%>">
            <input style="width:200px; height: 30px" type="submit" value="Volver al listado de cuentas" />
        </form>
    </th>
    <th><h1>Hola <%=cliente.getPrimerNombre()%></h1></th>
    </td>
</table>
<h1>Cambiar datos</h1>
<form:form method="post" action="/cajero/guardar" modelAttribute="cliente">
    <form:hidden path="id"/>
    <form:hidden path="tipo"/>
    <form:hidden path="idEmpresa"/>
    <form:hidden path="acceso"/>
    NIF: <form:input path="nif"/><br>
    Primer nombre: <form:input path="primerNombre"/><br>
    Segundo nombre: <form:input path="segundoNombre"/><br>
    Primer apellido: <form:input path="primerApellido"/><br>
    Segundo apellido: <form:input path="segundoApellido"/><br>
    Fecha de nacimiento: <form:input path="fechaNacimiento"/><br>
    Calle: <form:input path="calle"/><br>
    Numero: <form:input path="numero"/><br>
    Puerta: <form:input path="puerta"/><br>
    Ciudad: <form:input path="ciudad"/><br>
    Pais: <form:input path="pais"/><br>
    Region: <form:input path="region"/><br>
    Codigo postal: <form:input path="cp"/><br>
    Contrasena: <form:input path="contrasena"/><br>
    <form:button>Cambiar datos</form:button>
</form:form>

</body>
</html>
