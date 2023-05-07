<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.entity.EmpresaEntity" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    Cliente asociado = (Cliente) request.getAttribute("asociado");
    Empresa empresa = (Empresa) request.getAttribute("empresaEditada");
%>

<html>
<head>
    <title>Editar Datos Empresa</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Editar Datos Empresa: <%= empresa.getNombre() %> </h1>

            <form:form action="/empresa/guardarEmpresa?idEmpresa=<%= empresa.getIdEmpresa() %>" modelAttribute="empresaEditada" method="post"><br/>
                <form:hidden path="idEmpresa" value="<%= empresa.getIdEmpresa()%>"/>
                Nombre de la empresa: <form:input cssStyle="margin-bottom: 10px" path="nombre" required="true" /><br/>
                CIF de la empresa: <form:input cssStyle="margin-bottom: 10px" path="cif" required="true" /> <br/>
                Contrasena: <form:input cssStyle="margin-bottom: 10px" path="contrasenaEmpresa" required="true" /> <br/>
                <form action="/empresa/guardarEmpresa?idEmpresa=<%= empresa.getIdEmpresa() %>" method="post">
                    <input type="hidden" name="idAsociado" value="<%= asociado.getId() %>">
                    <button class="btn btn-dark">Guardar</button>
                </form>
            </form:form>

            <a href="/empresa/?id=<%= asociado.getId() %>">Volver al listado de empresas</a>
        </div>
    </div>
</div>

</body>
</html>