<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Cliente asociado = (Cliente) request.getAttribute("asociado");
%>

<html>
<head>
    <title>Movimientos Asociado</title>
</head>

<body>
<h1>Movimientos del asociado/a: <%= asociado.getPrimerNombre() %> con la cuenta de la empresa Y</h1>
</body>
</html>