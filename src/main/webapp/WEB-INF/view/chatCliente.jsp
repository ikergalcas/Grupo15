
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Chat" %>
<%@ page import="es.taw.taw23.dto.Mensaje" %>
<%@ page import="es.taw.taw23.service.MensajeService" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

<h1>Estas hablando con Iker</h1>

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
            <%
                } else {
            %>
                Yo:
            <%
                }
            %>
        </th>
        <td>
            <textarea cols="70" rows="5"><%=mensaje.getTexto()%></textarea>
        </td>
        <td valign="top"><%=mensaje.getFechaEnvio().toGMTString()%></td>
    </tr>
    <%
        }
    %>
</table>
<br>
<hr>
<form action="/asistente/enviar/<%=chat.getId()%>" method="post">
    <table>
        <tr>
            <th>Nuevo mensaje:</th> <td><textarea name="texto" rows="5" cols="70"></textarea></td>
            <td><button>Enviar mensaje</button></td>
        </tr>
    </table>
</form>
<a href="/asistente/<%=chat.getEmpleadoId()%>">Volver atras</a>
</body>
</html>