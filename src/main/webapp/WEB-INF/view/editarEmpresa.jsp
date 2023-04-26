<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.EmpresaEntity" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%
    Cliente asociado = (Cliente) request.getAttribute("asociado");
    Empresa empresa = (Empresa) request.getAttribute("empresaEditada");
%>

<html>
<head>
    <title>Editar Datos Empresa</title>
</head>

<body>
    <h1>Editar Datos Empresa: <%= empresa.getNombre() %> </h1>

<form:form action="/empresa/guardarEmpresa?idEmpresa=<%= empresa.getId() %>" modelAttribute="empresaEditada" method="post"><br/>
    <form:hidden path="id" value="<%= empresa.getId()%>"/>
    Nombre de la empresa: <form:input path="nombre"/><br/>
    <form action="/empresa/guardarEmpresa?idEmpresa=<%= empresa.getId() %>" method="post">
        <input type="hidden" name="idAsociado" value="<%= asociado.getId() %>">
        <button>Guardar</button>
    </form>
</form:form>
</body>
</html>