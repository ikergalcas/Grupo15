
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
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>
<body>


<div class="container">
    <div class="row">
        <div class="col">
            <jsp:include page="cabecera.jsp" />

            <h1>Asistente (<%=asistente.getNumero_empleado()%>)</h1>
            <br>
            <h2>Listado de chats</h2>
            <br>
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
                <form action="" method="post">
                    <button class="btn btn-primary mt-2">Filtrar</button>
                </form>
            </form:form>

            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>ESTADO</th>
                    <th>CLIENTE</th>
                    <th></th>
                </tr>
                <%
                    for (Chat chat: lista) {
                %>
                <tr
                <%
                    if (chat.getCerrado().equals(byteValue(1))) {
                %>
                class="bg-light"
                <%
                    }
                %>
                >
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
                <button class="btn btn-primary">Abir nuevo chat</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>