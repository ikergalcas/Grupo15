
<%
    String error = (String) request.getAttribute("error");
    Integer idAsociado = (Integer) request.getAttribute("idAsociado");
%>

<html>
<head>
    <title>error transferencia</title>
</head>

<body>
<h1>Error al hacer la transferencia. </h1>
<%
    if(error.equals("numeroCuenta")) {
%>
    <h1>El numero de cuenta introducido no existe</h1>
<%
    } else {
%>
    <h1>La cuenta origen no tiene suficiente dinero</h1>
<%
    }
%>
<a href="/empresa/transferencia?id=<%= idAsociado %>">Volver a hacer transferencia</a><br/>
<a href="/empresa/?id=<%= idAsociado %>">Ver listado de todos los asociados</a><br/>
<a href="/empresa/miEmpresa?id=<%= idAsociado %>">Ver listado de asociados de mi empresa</a><br/>
</body>
</html>