<!--Pablo Alarcón Carrión-->
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cuentas asociadas</title>
</head>
<% List<Cuenta> cuentasAsociadas = (List<Cuenta>) request.getAttribute("cuentasAsociadas");
    Cliente cliente = (Cliente) request.getAttribute("cliente");%>


<body>
<table>
    <td>
    <%
        if(cliente.getTipo().equals("individual")) {
    %>
    <th>
        <form action="/cliente/<%= cliente.getId() %>" method="get">
            <input style="width:200px; height: 30px" type="submit" value="Volver atrás" />
        </form>
    </th>
    <%
        } else {
    %>
    <th>
        <form action="/empresa/<%= cliente.getId() %>" method="get">
            <input style="width:200px; height: 30px" type="submit" value="Volver atrás" />
        </form>
    </th>
    <%
        }
    %>
    <th><h1>¡Hola <%=cliente.getPrimerNombre()%>!</h1></th>
    </td>
</table>
<h1 style="position: fixed; left: 43%">Cuentas asociadas</h1><br>
<table border="1" style="margin-top: 80px; position: fixed; left: 43%">
    <tr>
        <th>CUENTAS</th>
        <th>GESTIONAR</th>
    </tr>
    <% if (cuentasAsociadas==null ||cuentasAsociadas.isEmpty()){ %>
    <tr>
        <th>Sin cuentas asociadas</th>
        <th></th>

    </tr>
    <% } else {
            for (Cuenta x : cuentasAsociadas) { %>
            <tr>
            <th><%=x.getNumeroCuenta()%></th>
                <th>
                    <form action="/cajero/<%=cliente.getId()%>/cuenta/<%=x.getId()%>">
                        <input style="width:150px; height: 20px; margin-top:15px" type="submit" value="Acceder a la cuenta" />
                    </form>
                </th>
            </tr>
            <% }
         }%>
</table>
</body>
</html>
