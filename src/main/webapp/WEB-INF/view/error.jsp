<%@ page import="es.taw.taw23.dto.Cliente" %>
<%
    String error = (String) request.getAttribute("error");
    Integer idAsociado = (Integer) request.getAttribute("idAsociado");
    Cliente cliente = (Cliente) request.getAttribute("cliente");

%>
<%--
    Autor: Alvaro Yuste Moreno y Rocío Gómez Mancebo
--%>
<html>
<head>
    <title>error</title>
</head>

<body>
<h1>Error</h1> <br/>
<%
    if(error.equals("cuentaDestino")) {
%>
    <h1>La cuenta introducida no existe o esta bloqueada/desactivada</h1>
<%
    } else if(error.equals("cuentaOrigen")){
%>
    <h1>Por favor, selecciona una cuenta con la que realizar la transferencia</h1>
<%
    } else if(error.equals("dineroInsuficiente")){
%>
    <h1>La cuenta origen no tiene suficiente dinero</h1>
<%
    } if(error.equals("cuentaDestino") || error.equals("dineroInsuficiente")) {
%>
    <a href="/empresa/transferencia?id=<%= idAsociado %>">Volver a hacer transferencia</a><br/>
<%
    }
%>
<a href="/empresa/?id=<%= idAsociado %>">Volver al inicio de pagina</a><br/>
<a href="/cliente/<%= cliente.getId() %>">Volver al inicio de pagina de cliente</a><br/>
<a href="/empresa/miEmpresa?id=<%= idAsociado %>">Ver listado de asociados de mi empresa</a><br/>
</body>
</html>