<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Solicitud alta empresa</title>
</head>
<body>

<%
    Solicitud solicitud = (Solicitud) request.getAttribute("solicitud");
    Empleado gestor = (Empleado) request.getAttribute("gestor");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    Empresa empresa = (Empresa) request.getAttribute("empresa");
    Timestamp fechaApertura = new Timestamp(System.currentTimeMillis());
%>

<h2>Solicitud de dada de alta de la empresa <%=empresa.getNombre()%></h2>

<div>
    <strong>Nombre del cliente:</strong>   <%= cliente.getPrimerNombre() %> <% if (cliente.getPrimerApellido() != null) {%> <%=cliente.getPrimerApellido()%> <% } %>
</div>
<div>
    <strong>Tipo de solicitud:</strong>   <%= solicitud.getTipo_solicitud().getTipo()%>
</div>

<form:form action="/gestor/crearCuentaBancariaClienteEmpresa" method="post" modelAttribute="cuenta">
    <form:hidden path="idSolicitud" value="<%=solicitud.getId()%>"></form:hidden>
    <form:hidden path="idGestor" value="<%=gestor.getId()%>"></form:hidden>
    <form:hidden path="idCliente" value="<%=cliente.getId()%>"></form:hidden>
    <form:hidden path="idEmpresa" value="<%=empresa.getIdEmpresa()%>"></form:hidden>
    Numero a asociar a la cuenta de empresa: <form:input path="numeroCuenta"></form:input><br/>
    <form:hidden path="fechaApertura" value="<%=fechaApertura%>"></form:hidden>
    <br/>
    <form action="/gestor/crearCuentaBancariaClienteEmpresa" method="post">
        Numero a asociar a la cuenta individual del socio: <input type="text" name="numCuentaSocio"> <br/>
        <button>Aceptar Solicitud</button>
    </form>
</form:form>
</body>
</html>
