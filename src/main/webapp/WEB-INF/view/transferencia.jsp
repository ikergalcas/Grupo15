
<%@ page import="java.util.List" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
    <form:button>Hacer transferencia</form:button>
</form:form>

</body>
</html>