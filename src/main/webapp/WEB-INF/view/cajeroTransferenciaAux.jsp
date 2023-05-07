<!--Pablo Alarcón Carrión-->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="Importe" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transferir dinero</title>
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
    <h1 style="margin-left: 36.3%"><u>Realizar transferencia de dinero</u></h1>
    <table style="margin-left: 32%; margin-top: 10px">
        <td>
        <th>
            <form:form modelAttribute="movimiento" action="/cajero/transferir" method="post">
                <form:hidden path="cuentaOrigen"/>
                Importe: <form:input path="importeOrigen"/>
                Cuenta: <form:input path="cuentaDestino"/>
                <form action="/cajero/transferir" method="post">
                    <input type="hidden" name="idCliente" value="<%=cliente.getId()%>"/>
                    <input type="hidden" name="idCuenta" value="<%=cuenta.getId()%>"/>
                    <button>Transferir</button>
                </form>
            </form:form>
        </th>
        </td>
    </table>
    <table style="margin-left: 40%">
        <td><h2 style="background: red">Importe o cuenta inválidos</h2></td>
    </table>


    <table style="margin-left:40%">
        <td style="background-color:lightblue"><h1>SALDO: <%=cuenta.getDinero()%> <%=cuenta.getMoneda()%></h1></td>
    </table>

</body>
</html>
