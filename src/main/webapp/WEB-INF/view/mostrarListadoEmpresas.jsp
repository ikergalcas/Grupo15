<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Carla Serracant Guevara
--%>
<html>
<head>
    <title>Listado empresas</title>
</head>
<body>


<h1>Listado de empresas</h1>

<%
    List<Empresa> empresas = (List<Empresa>) request.getAttribute("empresas");
    Integer idGestor = (Integer) request.getAttribute("idGestor");
%>

<form:form method="post" action="/gestor/listaDeEmpresas/Ordenado" modelAttribute="filtro">
    Introduzca el cif:
    <form:input path="cif"></form:input>
    <form:hidden path="idGestor" value="<%=idGestor%>"></form:hidden>
    <form:button>Filtrar</form:button>
</form:form>
<table border="1">
    <tr>
        <th>CIF</th>
        <th>NOMBRE DE LA EMPRESA</th>
        <th>AUTORIZADOS Y SOCIOS DE LA EMPRESA</th>
    </tr>

    <% for (Empresa e : empresas) {%>
    <tr>
        <td><%=e.getCif()%></td>
        <td>
            <%=e.getNombre()%>
        </td>
        <td>
            <% for (Cliente c : e.getListaClientes()) {%>
            <a href="/gestor/verCliente/<%=c.getId()%>/<%=idGestor%>"><%=c.getPrimerNombre()%> <%=c.getPrimerApellido()%></a> <br/>
            <% } %>
        </td>
        <td>
            <input type="submit" value="Ver empresa" onclick="location.href='/gestor/verEmpresa/<%=e.getIdEmpresa()%>/<%=idGestor%>'">
        </td>
    </tr>
    <% } %>
</table>
<input type="submit" value="VOLVER ATRÁS" onclick="location.href='/gestor/<%=idGestor%>'">
</body>
</html>