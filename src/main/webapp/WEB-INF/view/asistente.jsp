
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Chat" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="static org.aspectj.runtime.internal.Conversions.byteValue" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
    Autor: Iker Gálvez Castillo
--%>

<%
    List<Chat> lista = (List<Chat>) request.getAttribute("chats");
    Empleado asistente = (Empleado) request.getAttribute("asistente");
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
%>
<html>
<head>
    <title>Asistente</title>
</head>
<body>


<h1>Asistente (<%=asistente.getNumero_empleado()%>)</h1>
<h2>Listado de chats</h2>

<form:form action="/asistente/filtrar" method="post" modelAttribute="filtro">
    <form:hidden path="id" value="<%=asistente.getId()%>"></form:hidden>
    Buscar por nif del cliente:
    <form:input path="nif"></form:input><br>
    Ordenar por:
    <form:select path="estado">
        <form:option value="<%=null%>"></form:option>
        <form:option value="<%=byteValue(0)%>">Abierto</form:option>
        <form:option value="<%=byteValue(1)%>">Cerrado</form:option>
    </form:select><br>
    <form:button>Filtrar</form:button>
</form:form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>ESTADO</th>
        <th>CLIENTE</th>
        <th></th>
    </tr>
    <%
        for (Chat chat: lista) {
    %>
    <tr>
        <td><%=chat.getId()%></td>
        <td>
            <%
                if (chat.getCerrado().equals(byteValue(1))) {
            %>
                Cerrado
            <%
                } else {
            %>
                Abierto
            <%
                }
            %>
        </td>
        <td><%= chat.getCliente() %></td>
        <td>
            <a href="/asistente/chatAsistente/<%=chat.getId()%>">Acceder</a>
        </td>
    </tr>
    <%
        }
    %>
</table border="1">
<br>
<form action="/asistente/abrirChatConCliente/<%=asistente.getId()%>" method="post">
    NIF Cliente:
    <select name="cliente" >
        <%
            for (Cliente cliente : clientes) {
        %>
        <option value="<%=cliente.getId()%>"><%= cliente.getNif() %></option>
        <%
            }
        %>
    </select>
    <button>Abir nuevo chat</button>
</form>

<br>
</body>
</html>