
<%--
    Autor: Rocío Gómez Mancebo
--%>

<%
    String error = (String) request.getAttribute("error");
    Integer idCliente = (Integer) request.getAttribute("idCliente");
%>

<html>
<head>
    <title>Error transferencia</title>
</head>

<body>
<h1>Error al hacer la transferencia. </h1>
<%
    if(error.equals("cuentaDestino")) {
%>
<h1>El numero de cuenta introducido no existe o se ha intentado realizar una transferencia erronea</h1>
<%
} else if(error.equals("cuentaDestinoBloqueadaOInactiva")) {
%>
<h1>La cuenta destino esta inactiva o bloqueada</h1>
<%
    }else{
%>
<h1>La cuenta origen no tiene suficiente dinero</h1>
<%
    }
%>
<a href="/cliente/transferenciaCliente/<%=idCliente %>">Volver a hacer transferencia</a><br/>
<a href="/cliente/<%= idCliente %>">Volver a pagina de movimientos</a><br/>
</body>
</html>