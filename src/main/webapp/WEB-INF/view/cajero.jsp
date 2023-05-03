<!--Pablo Alarcón Carrión-->
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cajero</title>
</head>
<body>
<% Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
   List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
 Cliente cliente = (Cliente) request.getAttribute("cliente");%>
<table>
    <td>
        <th>
        <form action="/cajero/<%=cliente.getId()%>">
            <input style="width:200px; height: 30px" type="submit" value="Volver al listado de cuentas" />
        </form>
        </th>
        <th style="width:850px"></th>
        <th style="width:800px"><h1>¡Hola <%=cliente.getPrimerNombre()%>!</h1></th>
        <th style="width:900px"></th>
        <th>
            <form action="/cajero/<%=cliente.getId()%>/modificar/">
                <input style="width:200px; height: 30px" type="submit" value="Modificar datos" />
            </form>
        </th>
    </td>
</table>


<table border="1" style="margin-left: 25%">
    <tr>
        <th style="width:200px">MOVIMIENTOS</th>
        <th style="width:300px">IMPORTE</th>
        <th style="width:200px">FECHA</th>
    </tr>
    <% if (movimientos==null){%>
    <tr>
        <th>Sin movimientos</th>
        <th>-</th>
        <th>-</th>
    </tr>
    <%} else {
            for (Movimiento mov : movimientos) {
                if (mov.getTipo().equals("pago")) { %>
                <tr>
                    <th>De <%=mov.getCuentaOrigen()%> a <%=mov.getCuentaDestino()%></th>
                    <th><u>Origen:</u> <%=mov.getImporteOrigen()%> <%=mov.getDivisaCuentaOrigen()%> <u>Destino:</u> <%=mov.getImporteDestino()%> <%=mov.getDivisaCuentaDestino()%></th>
                    <th><%=mov.getTimeStamp().toString()%></th>
                </tr>
                <% } else {
                    if (mov.getTipo().equals("cambioDivisa")) { %>
                    <tr>
                        <th>Cambio de divisa</th>
                        <th>Divisa cambiada de <%=mov.getDivisaCuentaOrigen()%> a <%=mov.getDivisaCuentaDestino()%></th>
                        <th><%=mov.getTimeStamp().toString()%></th>
                    </tr>
                    <% } else {
                        if (mov.getTipo().equals("sacarDinero")) { %>
                        <tr>
                            <th>Retirada de dinero</th>
                            <th><u>Cantidad:</u> -<%=(mov.getImporteOrigen()-mov.getImporteDestino())%> <%=mov.getDivisaCuentaOrigen()%></th>
                            <th><%=mov.getTimeStamp().toString()%></th>
                        </tr>
                        <% }
                        }
                    }
                }
            }%>
    </tr>
</table>

<table style="margin-left: 30%">
    <td>
        <th>
            <form action="/cajero/<%=cliente.getId()%>/cuenta/<%=cuenta.getId()%>/transferencia">
                <input style="width:200px; height: 30px" type="submit" value="Realizar una transferencia" />
            </form>
        </th>
        <th>
            <form action="/cajero/<%=cliente.getId()%>/cuenta/<%=cuenta.getId()%>/retirar">
                <input style="width:200px; height: 30px" type="submit" value="Retirar dinero" />
            </form>
        </th>
        <th>
            <form action="/cajero/<%=cliente.getId()%>/cuenta/<%=cuenta.getId()%>/cambiarDivisa">
                <input style="width:205px; height: 30px" type="submit" value="Cambiar divisa" />
            </form>
        </th>
    </td>
</table>

<table style="margin-left: 40%">
    <th>Estado cuenta: </th>
    <% if (cuenta.getEstadoCuenta().equals("activa")){ %>
    <th><h4 style="background-color: chartreuse; margin-top: 20px"><%=cuenta.getEstadoCuenta()%></h4></th>
    <%} else { %>
    <th><h4 style="background-color: red; margin-top: 20px"><%=cuenta.getEstadoCuenta()%></h4></th>
    <% }if (cuenta.getEstadoCuenta().equals("bloqueada")){ %>
    <th>
        <form action="/cajero/<%=cliente.getId()%>/solicitud/<%=cuenta.getId()%>/cambiarDivisa/<%=cuenta.getId()%>">
            <input style="width:205px; height: 20px; margin-top:15px" type="submit" value="Desbloquear cuenta" />
        </form>
    </th>
    <%} else{ %>
    <th><button type="button" disabled>Desbloquear cuenta</button></th>
    <%}%>
</table> <br>

<table style="position: fixed; margin-left:40%">
    <td style="background-color:lightblue"><h1>SALDO: <%=cuenta.getDinero()%> <%=cuenta.getMoneda()%></h1></td>
</table>

</body>
</html>
