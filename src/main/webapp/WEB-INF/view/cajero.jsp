<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!--Pablo Alarcón Carrión-->
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cajero</title>
</head>
<body>
<% Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
   List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
 Cliente cliente = (Cliente) request.getAttribute("cliente");
Solicitud solicitud = (Solicitud) request.getAttribute("solicitud");%>


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

<%if (!cuenta.getEstadoCuenta().equals("inactiva")) { %>

<form:form action="/cajero/filtrar" method="post" modelAttribute="filtroCajero">
    <input type="hidden" name="clienteId" value="<%=cliente.getId()%>"/>
    <input type="hidden" name="idCuenta" value="<%=cuenta.getId()%>"/>
    Filtrar por: <br>
    <li>Moneda en uso:
    <form:select multiple="false" path="filtrarPorDivisa">
        <form:option value="" label=""/>
        <form:options items="${monedas}"/>
    </form:select>
    </li>

    <li>Cuenta implicada en el movimiento:
        <form:select multiple="false" path="filtrarPorNumeroDeCuenta">
            <form:option value="" label=""/>
            <form:options items="${todasLasCuentas}"/>
        </form:select>
    </li>

    <li>Es un movimiento del tipo:
        <form:select multiple="false" path="filtrarPorMovimiento">
            <form:option value="" label=""/>
            <form:option value="pago" label="pago"/>
            <form:option value="cambioDivisa" label="cambioDivisa"/>
            <form:option value="sacarDinero" label="sacarDinero"/>
        </form:select>
    </li>

    <li>Ordenar por:
            <form:checkbox path="ordenarFecha" label="Fecha de la operación"/>
            <form:checkbox path="ordenarTipo" label="Tipo de transacción"/>
            <form:checkbox path="ordenarImporte" label="Importe en uso"/>
    </li>
    <form:button>Filtrar</form:button>
</form:form>

<table border="1" style="margin-left: 30%">
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
<% if(cuenta.getEstadoCuenta().equals("activa")){ %>
<table style="margin-left: 32.6%">
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
<%} else if (cuenta.getEstadoCuenta().equals("bloqueada")){%>
<table style="margin-left: 34.3%">
    <td>
    <th><button type="button" disabled style="margin-bottom: 3%; height: 30px; width: 180px">Realizar una transferencia</button></th>
    <th><button type="button" disabled style="margin-bottom: 3%; height: 30px; width: 180px">Retirar dinero</button></th>
    <th><button type="button" disabled style="margin-bottom: 3%; height: 30px; width: 180px">Cambiar divisa</button></th>
    </td>
</table>
    <%}%>
<table style="margin-left: 40%">
    <th>Estado cuenta: </th>
    <% if (cuenta.getEstadoCuenta().equals("activa") || (solicitud!=null && solicitud.getEstado_solicitud().getEstado().equals("aceptada"))){ %>
    <th><h4 style="background-color: chartreuse; margin-top: 20px"><%=cuenta.getEstadoCuenta()%></h4></th>
    <th><button type="button" disabled style="margin-bottom: 3%">Desbloquear cuenta</button> </th>
    <%} else {
        if (solicitud!=null){ %>
            <th><h4 style="background-color: yellow; margin-top: 20px">Pendiente</h4></th>
            <th><button type="button" disabled style="margin-bottom: 3%">Desbloquear cuenta</button> </th>
        <%} else {
                if (cuenta.getEstadoCuenta().equals("bloqueada")){%>
                <th><h4 style="background-color: red; margin-top: 20px"><%=cuenta.getEstadoCuenta()%></h4></th>
                <th>
                    <form:form method="post" action="/cajero/solicitud">
                        <form method="post" action="/cajero/solicitud">
                            <input type="hidden" name="idCliente" value="<%=cliente.getId()%>"/>
                            <input type="hidden" name="idCuenta" value="<%=cuenta.getId()%>">
                            <button style="margin-top: 10%">Desbloquear cuenta</button>
                        </form>
                    </form:form>
                </th>
                <%}
            }
        }%>
</table> <br>

<table style="position: fixed; margin-left:41.3%">
    <td style="background-color:lightblue"><h1>SALDO: <%=cuenta.getDinero()%> <%=cuenta.getMoneda()%></h1></td>
</table>

<%}%>
</body>
</html>
