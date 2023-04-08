<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.Empresa" %>
<%@ page import="es.taw.taw23.entity.Cliente" %>
<%
    Cliente asociado = (Cliente) request.getAttribute("asociado");
    Empresa empresa = (Empresa) request.getAttribute("empresaEditada");
%>

<html>
<head>
    <title>Editar Datos Empresa</title>
</head>

<body>
    <h1>Editar Datos Empresa del cliente <%= asociado.getPrimerNombre()%></h1>

<form:form action="/empresa/guardarEmpresa?idEmpresa=<%= empresa.getIdEmpresa()%>" modelAttribute="empresaEditada" method="post"><br/>
    <form:hidden path="idEmpresa" value="<%= empresa.getIdEmpresa()%>"/><br/>
    <form:input path="nombre"/><br/>
    <form:button>Guardar cambios</form:button>
</form:form>
</body>
</html>