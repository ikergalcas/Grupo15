<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cajero</title>
</head>
<body>
<% Cuenta x = (Cuenta) request.getAttribute("cuenta");
   List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos"); %>
<table>
    <td>
        <th style="width:600px"><h1>¡Bienvenidx al cajero!</h1></th>
        <th style="width:900px"></th>
        <th>
            <form action="/cajero/modificar/<%=x.getId()%>">
                <input style="width:200px; height: 30px" type="submit" value="Modificar datos" />
            </form>
        </th>
    </td>
</table>


<table border="1" style="margin-left: 30%">
    <tr>
        <th style="width:200px">MOVIMIENTOS</th>
        <th style="width:200px">IMPORTE</th>
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
                    <th><u>Origen:</u> <%=mov.getImporteOrigen()%> <u>Destino:</u> <%=mov.getImporteDestino()%></th>
                    <th><%=mov.getTimeStamp().toString()%></th>
                </tr>
                <% } else {
                    if (mov.getTipo().equals("cambioDivisa")) { %>
                    <tr>
                        <th>Cambio de divisa</th>
                        <th><u>De:</u><%//%><u>A:</u><%//%></th>
                        <th><%=mov.getTimeStamp().toString()%></th>
                    </tr>
                    <% } else {
                        if (mov.getTipo().equals("sacarDinero")) { %>
                        <tr>
                            <th>Retirada de dinero</th>
                            <th><u>Cantidad:</u><%//%></th>
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
            <form action="/cajero/transferencia/<%=x.getId()%>">
                <input style="width:200px; height: 30px" type="submit" value="Realizar una transferencia" />
            </form>
        </th>
        <th>
            <form action="/cajero/retirar/<%=x.getId()%>">
                <input style="width:200px; height: 30px" type="submit" value="Retirar dinero" />
            </form>
        </th>
        <th>
            <form action="/cajero/cambiarDivisa/<%=x.getId()%>">
                <input style="width:205px; height: 30px" type="submit" value="Cambiar divisa" />
            </form>
        </th>
    </td>
</table>

<table style="margin-left: 40%">
    <th>Estado cuenta: </th>
    <th><%=x.getEstadoCuenta()%></th>
    <% if (x.getEstadoCuenta().equals("2")){ %>
    <th><a href="/solicitud?id=<%=x.getId()%>" type="button" id="Desbloquear">Desbloquear cuenta</a></th>
    <%} else{ %>
    <th><button type="button" disabled>Desbloquear cuenta</button></th>
    <%}%>
</table>
</body>
</html>
