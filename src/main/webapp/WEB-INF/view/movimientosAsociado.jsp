<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    Cliente asociado = (Cliente) request.getAttribute("asociado");
    Empresa empresa = (Empresa) request.getAttribute("empresa");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    List<Movimiento> movimientoList = (List<Movimiento>) request.getAttribute("movimientos");
%>

<html>
<head>
    <title>Movimientos Asociado</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Movimientos del asociado/a <%= asociado.getPrimerNombre() %> con la cuenta de la empresa <%= empresa.getNombre() %></h1>

            <from:form method="post" action="/empresa/filtrarMovimiento" modelAttribute="movimientoFiltro">
                Filtrar por:
                <form:hidden path="cuentaOrigen" />
                <form:hidden path="asociado" value="<%= asociado.getId() %>" />
                <form:hidden path="timeStamp" value="<%= new java.sql.Timestamp(System.currentTimeMillis()) %>" /> <br/>
                Cuenta destino: <form:input cssStyle="margin-bottom: 10px" path="cuentaDestino" /> <br/>
                Moneda origen:
                <form:select cssStyle="margin-bottom: 10px" path="divisaCuentaOrigen">
                    <form:option label="" value="" />
                    <form:options items="${monedas}" itemLabel="moneda" itemValue="moneda" />
                </form:select> <br/>
                Moneda destino:
                <form:select cssStyle="margin-bottom: 10px" path="divisaCuentaDestino">
                    <form:option label="" value="" />
                    <form:options items="${monedas}" itemValue="moneda" itemLabel="moneda" />
                </form:select> <br/>
                Tipo de movimiento:
                <form:select cssStyle="margin-bottom: 10px" path="tipo">
                    <form:option label="" value="" />
                    <form:option label="pago" value="pago"/>
                    <form:option label="cambio divisa" value="cambioDivisa" />
                </form:select> <br/>
                <br/>
                <form method="post" action="/empresa/filtrarMovimiento">
                    Ordenar por: <br/>
                    Importe origen <input style="margin-bottom: 10px" type="checkbox" name="opciones[]" value="origen" /> <br/>
                    Importe destino <input style="margin-bottom: 10px" type="checkbox" name="opciones[]" value="destino" /> <br/>
                    Fecha <input style="margin-bottom: 10px" type="checkbox" name="opciones[]" value="fecha" /> <br/>
                    Orden:
                    <select style="margin-bottom: 10px" name="orden">
                        <option value="ascendente">Ascendente</option>
                        <option value="descendente">Descendente</option>
                    </select>
                    <button class="btn btn-dark">Filtrar</button>
                </form>
            </from:form>

            <table class="table">
                <th>Fecha</th>
                <th>Importe Origen</th>
                <th>Importe Destino</th>
                <th>Cuenta Origen</th>
                <th>Cuenta Destino</th>
                <th>Tipo de movimiento</th>
                <th>Moneda Origen</th>
                <th>Moneda Destino</th>

                <%
                    for(Movimiento movimiento : movimientoList) {
                        if(movimiento.getAsociado() == asociado.getId()) {
                %>
                <tr>
                    <td><%= movimiento.getTimeStamp() %></td>
                    <td><%= movimiento.getImporteOrigen() %></td>
                    <td><%= movimiento.getImporteDestino() %></td>
                    <td><%= movimiento.getCuentaOrigen() %></td>
                    <td><%= movimiento.getCuentaDestino() %></td>
                    <td><%= movimiento.getTipo() %></td>
                    <td><%= movimiento.getDivisaCuentaOrigen() %></td>
                    <td><%= movimiento.getDivisaCuentaDestino() %></td>
                </tr>
                <%
                        }
                    }
                %>
            </table>

            <form style="margin-top: 10px" action="/empresa/miEmpresa" method="get">
                <input type="hidden" name="id" value="<%= asociado.getId() %>">
                <button class="btn btn-primary">Volver</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>