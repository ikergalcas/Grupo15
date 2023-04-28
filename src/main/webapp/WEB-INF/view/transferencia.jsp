
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
    Cliente asociado = (Cliente) request.getAttribute("asociado");
%>

<html>
<head>
    <title>Transferencia</title>
</head>

<body>

<h1>Transferencia Bancaria</h1>


<form:form action="/empresa/guardarTransferencia" method="post" modelAttribute="transferencia">
    Importe: <form:input path="importe" /> <br/>
    Numero de Cuenta Origen:
    <form:select path="cuentaOrigen">
        <form:options items="${cuentas}" itemValue="numeroCuenta" itemLabel="numeroCuenta" />
    </form:select> <br/>
    Numero de Cuenta Destino: <form:input path="cuentaDestino" /> <br/>
    <form action="/empresa/guardarTransferencia" method="post">
        <input type="hidden" name="idAsociado" value="<%= asociado.getId() %>">
        <button>Hacer transferencia</button>
    </form>
</form:form>

</body>
</html>