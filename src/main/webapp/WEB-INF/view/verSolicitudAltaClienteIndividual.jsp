<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Solicitud alta cliente</title>
</head>
<body>
    <%
        Solicitud solicitud = (Solicitud) request.getAttribute("solicitud");
        Empleado gestor = (Empleado) request.getAttribute("gestor");
        Cliente cliente = (Cliente) request.getAttribute("cliente");
        Timestamp fechaApertura = new Timestamp(System.currentTimeMillis());
    %>

    <h2>Solicitud del cliente con nif <%=cliente.getNif()%></h2>
    <div>
        <strong>Nombre del cliente:</strong>   <%= cliente.getPrimerNombre() %> <% if (cliente.getPrimerApellido() != null) {%> <%=cliente.getPrimerApellido()%> <% } %>
    </div>
    <div>
        <strong>Tipo de solicitud:</strong>   <%= solicitud.getTipo_solicitud().getTipo()%>
    </div>

    <form:form action="/gestor/crearCuentaBancariaClienteIndividual" method="post" modelAttribute="cuenta">
        <form:hidden path="idSolicitud" value="<%=solicitud.getId()%>"></form:hidden>
        <form:hidden path="idGestor" value="<%=gestor.getId()%>"></form:hidden>
        <form:hidden path="idCliente" value="<%=cliente.getId()%>"></form:hidden>
        Numero a asociar a la nueva cuenta: <form:input path="numeroCuenta"></form:input><br/>
        <form:hidden path="fechaApertura" value="<%=fechaApertura%>"></form:hidden>

        <button>Aceptar solicitud</button>
    </form:form>

</body>
</html>
