
<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

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
    if(error.equals("coinciden")) {
%>
    <h1>La cuenta origen y la cuenta destino deben ser distintas</h1>
<%
    } else if(error.equals("importeIncorrecto")) {
%>
    <h1>El importe introducido debe ser mayor de 0</h1>
<%
    } else if(error.equals("dineroInsuficiente")){
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