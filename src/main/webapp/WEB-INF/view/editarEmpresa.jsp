<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.EmpresaEntity" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%
    Cliente asociado = (Cliente) request.getAttribute("asociado");
    EmpresaEntity empresa = (EmpresaEntity) request.getAttribute("empresaEditada");
%>

<html>
<head>
    <title>Editar Datos Empresa</title>
</head>

<body>
    <h1>Editar Datos Empresa del cliente <%= asociado.getPrimerNombre()%></h1>

<form:form action="/empresa/guardarEmpresa?idEmpresa=<%= empresa.getId() %>" modelAttribute="empresaEditada" method="post"><br/>
    <form:hidden path="id" value="<%= empresa.getId()%>"/><br/>
    <form:input path="nombre"/><br/>
    <form:button>Guardar cambios</form:button>
</form:form>
</body>
</html>