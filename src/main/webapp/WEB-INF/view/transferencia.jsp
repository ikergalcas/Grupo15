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


<form:form action="/empresa/guardarTransferencia" method="post" modelAttribute="transferencia">
    Importe: <form:input path="importe" /><br/>
    Selecciona la cuenta con la que quieres realizar la transferencia:
    <form:select path="cuenta" >
        <form:option value="" label="------" />
        <form:options items="<%= cuentas %>" itemLabel="numeroCuenta" /><br/>
    </form:select>
    <br/>
    <form action="/empresa/guardarTransferencia" method="post">
        Numero de Cuenta Destino: <input type="text" name="numeroCuenta">
        <form:button>Hacer transferencia</form:button>
    </form>

</form:form>

</body>
</html>