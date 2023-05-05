
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    Cliente asociado = (Cliente) request.getAttribute("asociado");
%>

<html>
<head>
    <title>Transferencia Empresa</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Transferencia Bancaria</h1>


            <form:form action="/empresa/guardarTransferencia" method="post" modelAttribute="transferencia">
                <form:hidden path="importeDestino" />
                <form:hidden path="timeStamp" value="<%= new java.sql.Timestamp(System.currentTimeMillis()) %>" />
                <form:hidden path="tipo" />
                <form:hidden path="divisaCuentaOrigen" />
                <form:hidden path="divisaCuentaDestino" />
                Importe: <form:input path="importeOrigen" required="true" /> <br/>
                Numero de Cuenta Origen:
                <form:select path="cuentaOrigen">
                    <form:options items="${cuentas}" itemValue="numeroCuenta" itemLabel="numeroCuenta" />
                </form:select> <br/>
                Numero de Cuenta Destino:
                <form:select path="cuentaDestino">
                    <form:options items="${cuentasDestino}" itemValue="numeroCuenta" itemLabel="numeroCuenta" />
                </form:select> <br/>
                <form action="/empresa/guardarTransferencia" method="post">
                    <input type="hidden" name="idAsociado" value="<%= asociado.getId() %>">
                    <button class="btn btn-dark">Hacer transferencia</button>
                </form>
            </form:form>

            <a href="/empresa/?id=<%= asociado.getId() %>">Volver al listado de empresas</a>
        </div>
    </div>
</div>

</body>
</html>