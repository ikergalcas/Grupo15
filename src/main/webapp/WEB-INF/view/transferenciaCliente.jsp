
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>

<%--
    Autor: Rocío Gómez Mancebo
--%>

<html>
<head>
    <title>Transferencia</title>
</head>

<body>

<h1>Transferencia Bancaria</h1>


<form:form action="/cliente/guardarTransferenciaCliente" method="post" modelAttribute="transferenciaCliente">
    Importe: <form:input path="importe" /> <br/>
    Numero de Cuenta Origen:<%=cuenta.getNumeroCuenta()%> <form:hidden path="cuentaOrigen" value="<%=cuenta.getNumeroCuenta()%>"></form:hidden><br>
    Numero de Cuenta Destino: <form:input path="cuentaDestino" /> <br/>
    <form action="/cliente/guardarTransferenciaCliente" method="post">
        <input type="hidden" name="idClient" value="<%=cliente.getId()%>">
        <form:button>Hacer transferencia</form:button>
    </form>
</form:form>
<br>

<a href="/cliente/<%= cliente.getId() %>">Volver a pagina de movimientos</a><br/>
</body>
</html>