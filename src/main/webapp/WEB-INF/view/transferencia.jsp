<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="es.taw.taw23.entity.Cuenta" %>
<%@ page import="es.taw.taw23.entity.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.entity.Tipomovimiento" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Transferencia</title>
</head>

<body>

<h1>Transferencia Bancaria</h1>

<%
    Cliente asociado = (Cliente) request.getAttribute("asociado");
    LocalDateTime fechaActual = LocalDateTime.now();
    DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String timeS = fechaActual.format(formatoFechaHora);
    Tipomovimiento tipoMov = new Tipomovimiento();
    tipoMov.setId(1);
    tipoMov.setTipo("pago");
    List<Cuenta> cuentas = asociado.getCuentasById();
%>

<form:form action="/empresa/guardarTransferencia" method="post" modelAttribute="movimiento">
    <form:hidden path="timeStamp" value="<%= timeS %>" /><br/>
    <form:hidden path="monedaOrigen" value="Euro" /><br/>
    Importe: <form:input path="importeOrigen" /><br/>
    <form:hidden path="importeDestino" value="<%= 1 %>" />
    <form:hidden path="tipomovimientoByTipoMovimientoId" value="<%= tipoMov %>" />
    <form:select path="cuentaByCuentaIdCuenta" items="${cuentas}" />


</form:form>

</body>
</html>