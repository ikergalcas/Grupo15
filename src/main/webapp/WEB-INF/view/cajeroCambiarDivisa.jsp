<!--Pablo Alarcón Carrión-->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="button" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Divisa" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cambiar de divisa</title>
</head>
<body>
<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>


<table>
    <td>
    <th>
        <form action="/cajero/<%=cliente.getId()%>/cuenta/<%=cuenta.getId()%>">
            <input style="width:200px; height: 30px" type="submit" value="Volver atrás" />
        </form>
    </th>
    <th><h1>¡Hola <%=cliente.getPrimerNombre()%>!</h1></th>
    </td>
</table>
<h2>Cambiar divisa de la cuenta</h2>

<h2><u>Divisa actual:</u> <%=cuenta.getMoneda()%></h2>
<form:form method="post" modelAttribute="cuenta" action="/cajero/cambiar">
    <form:hidden path="id"/>
    <form:select path="moneda" items="${divisas}" itemLabel="moneda" itemValue="moneda"/>
    <form action="/cajero/cambiar" method="post">
        <input type="hidden" name="idCliente" value="<%=cliente.getId()%>"/>
        <button>Cambiar divisa</button>
    </form>
</form:form>
</body>
</html>
