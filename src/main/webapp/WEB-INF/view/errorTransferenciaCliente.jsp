
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
<h1>El numero de cuenta introducido no existe</h1>
<%
} else {
%>
<h1>La cuenta origen no tiene suficiente dinero</h1>
<%
    }
%>
<a href="/cliente/transferenciaCliente/<%=idCliente %>">Volver a hacer transferencia</a><br/>
<a href="/cliente/<%= idCliente %>">Volver a pagina de movimientos</a><br/>
</body>
</html>