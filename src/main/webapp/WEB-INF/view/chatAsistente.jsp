
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Chat" %>
<%@ page import="es.taw.taw23.dto.Mensaje" %>
<%@ page import="es.taw.taw23.service.MensajeService" %>
<%@ page import="static org.aspectj.runtime.internal.Conversions.byteValue" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
    Autor: Iker Gálvez Castillo
--%>

<%
    List<Mensaje> lista = (List<Mensaje>) request.getAttribute("mensajes");
    Chat chat = (Chat) request.getAttribute("chat");
    Mensaje nuevoMensaje = new Mensaje();
%>

<html>
<head>
    <title>Chat</title>
</head>
<body>

<h1>Chat con cliente: <%=chat.getCliente()%></h1>

<table>
    <%
        for (Mensaje mensaje : lista) {
    %>
    <tr>
        <th align="right" valign="top">
            <%
                if (mensaje.getRemitente().equals("empleado")) {
            %>
            Yo:
                <td>
                    <textarea cols="70" rows="5" readonly="true" style="background: #e6e6e6; margin-left: 8px"><%=mensaje.getTexto()%></textarea>
                </td>
            <%
                } else {
            %>
            Cliente:
                <td>
                    <textarea cols="70" rows="5" readonly="true" style="margin-left: 8px"><%=mensaje.getTexto()%></textarea>
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
<%
    if (chat.getCerrado().equals(byteValue(0))) {
%>
<br>
<hr>
<form action="/asistente/enviarMensajeAsistente/<%=chat.getId()%>" method="post">
    <table>
        <tr>
            <th>Nuevo mensaje:</th> <td><textarea name="texto" rows="5" cols="70"></textarea></td>
            <td><button>Enviar mensaje</button></td>
        </tr>
    </table>
</form>
<%
    }
%>
<br>
<a href="/asistente/<%=chat.getEmpleadoId()%>">Volver atras</a>
</body>
</html>