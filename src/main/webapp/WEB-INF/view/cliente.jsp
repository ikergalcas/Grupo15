<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Movimiento" %>
<%@ page import="es.taw.taw23.dto.Chat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
    Autor: Rocío Gómez Mancebo
--%>

<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
    boolean solicitudDesbloqueo = (boolean) request.getAttribute("solicitudDesbloqueo");
    boolean solicitudActivacion = (boolean) request.getAttribute("solicitudActivacion");
    Chat chat = (Chat) request.getAttribute("chat");
 %>
<html>
<head>
    <title>Titulo</title>
</head>

<body>

<h1>Cuenta de <%=cliente.getPrimerNombre()%></h1>
<h2> Estado acutal de la cuenta : <%=cuenta.getEstadoCuenta()%></h2>

<h2> Lista de MOVIMIENTOS</h2>
<br>
<form:form method="post" action="/cliente/filtrar" modelAttribute="filtro">
    Filtrar por:
    <br>
    Cuenta destino: <form:input path="cuentaDestino" />
    <br/>
    Moneda origen:
    <form:select path="divisaCuentaOrigen">
        <form:option label="" value="" />
        <form:options items="${divisas}" itemLabel="moneda" itemValue="moneda" />
    </form:select>
    <br/>
    Moneda destino:
    <form:select path="divisaCuentaDestino">
        <form:option label="" value="" />
        <form:options items="${divisas}" itemValue="moneda" itemLabel="moneda" />
    </form:select>
    <br/>
    Tipo de movimiento:
    <form:select path="tipo">
        <form:option label="" value="" />
        <form:option label="pago" value="pago"/>
        <form:option label="cambio divisa" value="cambioDivisa" />
        <form:option label="sacar dinero" value="sacarDinero" />

    </form:select> <br/>
        <form method="post" action="/cliente/filtrar">
            <input type="hidden" name="idClient" value="<%=cliente.getId()%>">
            Ordenar por: <br/>
            Importe origen<input type="checkbox" name="opciones[]" value="origen"/> <br/>
            Importe destino<input type="checkbox" name="opciones[]" value="destino" /> <br/>
            Orden:
            <select name="orden">
                <option value="ascendente">Ascendente</option>
                <option value="descendente">Descendente</option>
            </select>
        <button>Filtrar</button>
    </form>
</form:form>




<table border="1">
    <tr>
        <th>ID</th>
        <th>Cuenta Origen</th>
        <th>Cuenta Destino</th>
        <th>Importe de Origen</th>
        <th>Moneda de Origen</th>
        <th>Importe de Destino</th>
        <th>Moneda de Destino</th>
        <th>Tipo de movimiento</th>
        <th>Fecha </th>
    </tr>

    <%
        for(Movimiento m : movimientos){
    %>
    <tr>
        <td align="center"><%= m.getId()%></td>
        <td align="center"><%=m.getCuentaOrigen()%></td>
        <td align="center"><%=m.getCuentaDestino()%></td>
        <td align="center"><%=m.getImporteOrigen()%></td>
        <td align="center"><%=m.getDivisaCuentaOrigen()%></td><%--CAMBIAR EN UN FUTURO MOVIMIENTO TIPO MONEDA --%>
        <td align="center"><%=m.getImporteDestino().shortValue()%></td>
        <td align="center"><%=m.getDivisaCuentaDestino()%></td>
        <td align="center"><%=m.getTipo()%></td>
        <td align="center"><%=m.getTimeStamp()%></td>
    </tr>
    <%
        }
    %>

</table>

<br>
<%
    if(cuenta.getEstadoCuenta().equals("bloqueada") || cuenta.getEstadoCuenta().equals("inactiva")){
%>
<table border="1">
    <tr>
        <th>Dinero Actual</th>
        <th>Divisa</th>
    </tr>
    <tr>
        <th><%=cuenta.getDinero()%></th>
        <th><%=cuenta.getMoneda()%></th>
    </tr>
</table>
<br>

<a>Realizar transferencia (Deshabilitado)</a> <br/>
<a>Modificar mis datos (Deshabilitado)</a> <br/>


<%
    }else{
%>
<table border="1">
    <tr>
        <th>Dinero Actual</th>
        <th>Divisa</th>
        <th>Cambiar Divisa</th>
    </tr>
    <tr>
        <th><%=cuenta.getDinero()%></th>
        <th><%=cuenta.getMoneda()%></th>
        <th valign="center"> <form:form method="post" action="/cliente/cambioDivisa" modelAttribute="cambioDivisa">
            <form:hidden path="idCliente" value="<%= cliente.getId() %>" />
            <form:hidden path="cuenta" value="<%= cuenta.getNumeroCuenta() %>" />
            <form:select path="divisa">
                <form:options items="${divisas}" itemLabel="moneda" itemValue="moneda" />
            </form:select>
            <form:button>Cambiar la divisa</form:button>
        </form:form></th>
    </tr>
</table>
<br>

<a href="/cliente/transferenciaCliente/<%= cliente.getId() %>">Realizar transferencia</a> <br/>
<a href="/cliente/perfilCliente/<%=cliente.getId()%>" >Modificar mis datos</a> <br/>
<br/>
<br/>
<%
    if (chat == null) {
%>
<a href="/asistente/abrirChatConAsistente/<%=cliente.getId()%>">Abrir nuevo chat con asistente</a>
<%
} else {
%>
<a href="/asistente/chatCliente/<%=chat.getId()%>">Acceder a chat con asistente</a>
<%
    }
%>

<%
    if(!cliente.getTipo().equals("individual")) {
%>
    <a href="/empresa/?id=<%= cliente.getId() %>">Volver a la vista de empresa</a>
<%
    }
%>

<%
    }
%>
<%
    if(cuenta.getEstadoCuenta().equals("inactiva")){
        if(solicitudActivacion){
%>
    <p>La solicitud de activacion ya ha sido enviada</p>
<%
    }else{
%>
    <form action="/cliente/solicitarActivacion" method="post">
        <input type="hidden" name="idCliente" value="<%= cliente.getId() %>" />
        <button>Enviar solicitud de activacion de cuenta</button>
    </form>
<%
    }}
        if(cuenta.getEstadoCuenta().equals("bloqueada")){
        if(solicitudDesbloqueo){
%>
    <p>La solicitud de desbloqueo ya ha sido enviada</p>
<%
    }else{
%>

    <form action="/cliente/solicitarDesbloqueo" method="post">
        <input type="hidden" name="idCliente" value="<%= cliente.getId() %>" />
        <button>Enviar solicitud de desbloqueo de cuenta</button>
    </form>
<%
        }
    }
%>

<br/>
<a href="/cajero/<%= cliente.getId() %>">Acceder al cajero</a>

</body>
</html>