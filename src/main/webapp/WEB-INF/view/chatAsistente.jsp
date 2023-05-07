
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
    <title>Chat con cliente</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Chat con cliente: <%=chat.getCliente()%></h1>
            <br>
            <table>
                <%
                    for (Mensaje mensaje : lista) {
                %>
                <tr>
                    <%
                    if (mensaje.getRemitente().equals("empleado")) {
                    %>
                    <th valign="top">
                        Yo:
                    </th>
                    <td>
                        <textarea class="me-2 ms-2" cols="70" rows="5" readonly="true" style="background: #e6e6e6"><%=mensaje.getTexto()%></textarea>
                    </td>
                    <%
                    } else {
                    %>
                    <th valign="top">
                        Cliente:
                    </th>
                    <td>
                        <textarea class="me-2 ms-2" cols="70" rows="5" readonly="true"><%=mensaje.getTexto()%></textarea>
                    </td>
                    <%
                        }
                    %>
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
                        <th valign="top">Nuevo mensaje:</th>
                        <td><textarea class="ms-2" name="texto" rows="5" cols="70"></textarea></td>
                        <td><button class="btn btn-primary ms-2">Enviar mensaje</button></td>
                    </tr>
                </table>
            </form>
            <%
                }
            %>
            <br>
            <form action="/asistente/<%=chat.getEmpleadoId()%>" method="get">
                <button class="btn btn-secondary">Volver atras</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>