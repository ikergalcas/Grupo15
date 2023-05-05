
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Chat" %>
<%@ page import="es.taw.taw23.dto.Mensaje" %>
<%@ page import="es.taw.taw23.service.MensajeService" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
    Autor: Iker Gálvez Castillo
--%>

<%
    List<Mensaje> lista = (List<Mensaje>) request.getAttribute("mensajes");
    Chat chat = (Chat) request.getAttribute("chat");
    Empleado asistente = (Empleado) request.getAttribute("asistente");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    Mensaje nuevoMensaje = new Mensaje();
%>

<html>
<head>
    <title>Chat</title>
</head>
<body>

<h1>Estas hablando con: <%=asistente.getNombre()%></h1>

<table>
    <%
        for (Mensaje mensaje : lista) {
    %>
    <tr>
        <th align="right" valign="top">
            <%
                if (mensaje.getRemitente().equals("empleado")) {
            %>
                Asistente:
                <td>
                    <textarea cols="70" rows="5" readonly="true" style="margin-left: 8px"><%=mensaje.getTexto()%></textarea>
                </td>
            <%
                } else {
            %>
                Yo:
                <td>
                    <textarea cols="70" rows="5" readonly="true" style="background: #e6e6e6; margin-left: 8px"><%=mensaje.getTexto()%></textarea>
                </td>
            <%
                }
            %>
        </th>

        <td valign="top"><%=mensaje.getFechaEnvio()%></td>
    </tr>
    <%
        }
    %>
</table>
<br>
<hr>
<form action="/asistente/enviarMensajeCliente/<%=chat.getId()%>" method="post">
    <table>
        <tr>
            <th>Nuevo mensaje:</th> <td><textarea name="texto" rows="5" cols="70"></textarea></td>
            <td><button>Enviar mensaje</button></td>
        </tr>
    </table>
</form>
<%
    if (cliente.getTipo().equals("individual")) {
%>
<a href="/cliente/<%=chat.getClienteId()%>">Volver atras</a>
<%
    } else {
%>
<a href="/empresa/?id=<%=chat.getClienteId()%>">Volver atras</a>
<%
    }
%>
<br>
<br>
<a href="/asistente/cerrarChatCliente/<%=chat.getId()%>">Cerrar chat definitivamente</a>
</body>
</html>