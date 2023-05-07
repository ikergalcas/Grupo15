<%@ page import="es.taw.taw23.dto.Solicitud" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Empleado" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    Empleado gestor = (Empleado) request.getAttribute("gestor");
    Solicitud solicitud = (Solicitud) request.getAttribute("solicitud");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>
<h3>Se ha dado de alta correctamente al usuario <%=solicitud.getCliente().getNif()%></h3>
<div>
    Cuenta creada con fecha: <%=cuenta.getFechaApertura().toString()%>
</div>

<input type="submit" value="Volver a las solicitudes" onclick="location.href='/gestor/solicitudes/?id=<%=gestor.getId()%>'"/>
</body>
</html>
