<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<html>
<head>
    <title>Retirar fondos</title>
</head>
<body>
<%
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    Cliente cliente = (Cliente) request.getAttribute("cliente"); %>
<table>
    <td>
        <th>
            <form action="/cajero/<%=cliente.getId()%>/cuenta/<%=cuenta.getId()%>">
                <input style="width:200px; height: 30px" type="submit" value="Volver atras"/>
            </form>
        </th>
        <th><h1>Hola <%=cliente.getPrimerNombre()%></h1></th>
    </td>
</table>

<h1 style="margin-left: 43%"><u>Retirar fondos</u></h1>
<table style="margin-left: 30%; margin-top: 10px">
    <td>
        <th>Introduzca cantidad:</th>
        <th>
            <form:form modelAttribute="cuenta" action="/cajero/sacar" method="post">
                <form:hidden path="id"/>
                <form:input path="dinero"/>
                <form:button>Sacar dinero</form:button>
            </form:form>
        </th>
    </td>
</table>
<table style="margin-left: 44%; margin-top: 10px">
    <th><h2 style="background: red">Cantidad invalida</h2></th>
</table>


<table style="margin-left:40%">
    <td style="background-color:lightblue"><h1>SALDO: <%=cuenta.getDinero()%> <%=cuenta.getMoneda()%></h1></td>
</table>

</body>
</html>
