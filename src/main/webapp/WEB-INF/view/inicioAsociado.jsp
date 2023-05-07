<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.taw.taw23.dto.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="es.taw.taw23.dto.Cuenta" %>
<%@ page import="es.taw.taw23.dto.Empresa" %>
<%@ page import="es.taw.taw23.dto.Chat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    List<Cliente> listaAsociados = (List<Cliente>) request.getAttribute("asociados");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
    List<Empresa> empresas = (List<Empresa>) request.getAttribute("empresas");
    boolean solicitudDesbloqueo = (boolean) request.getAttribute("solicitudDesbloqueo");
    boolean solicitudActivacion = (boolean) request.getAttribute("solicitudActivacion");
    Chat chat = (Chat) request.getAttribute("chat");
%>
<html>

<style>

    /* Estilo base para el select */
    select {
        padding: 8px;
        border-radius: 4px;
        border: 1px solid #ccc;
        font-size: 16px;
    }

    .btn1:hover {
        background-color: #00c1ff;
    }

    .btn2:hover {
        background-color: #00e0ac;
    }

    .btn3:hover {
        background-color: #b6ec5a;
    }

    .btn4:hover {
        background-color: #ffd368;
    }
</style>

<head>
    <title>Inicio Empresa</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<jsp:include page="cabecera.jsp" />

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Bienvenido/a <%= cliente.getPrimerNombre() %></h1>

            <p>Estas son tus cuentas:</p>

            <%
                for(Cuenta cuenta : cuentas) {
            %>
                <div class="card" style="margin-bottom: 20px">
                    <div class="card-header">
                        <h4 style="text-align: center">Numero de cuenta: <%= cuenta.getNumeroCuenta() %></h4>
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <tr>
                                <td style="text-align: center">Dinero</td>
                                <td style="text-align: center">Fecha de apertura</td>
                                <td style="text-align: center">Estado</td>
                                <td style="text-align: center">Tipo</td>
                                <td style="text-align: center;">Divisa</td>
                                <td style="text-align: center"></td>
                                <td style="text-align: center"></td>
                            </tr>
                            <tr>
                                <td style="text-align: center"><%= cuenta.getDinero() %></td>
                                <td style="text-align: center"><%= cuenta.getFechaApertura() %></td>
                                <td style="text-align: center">
                                    <%= cuenta.getEstadoCuenta() %>
                                    <%
                                        if(cuenta.getEstadoCuenta().equals("inactiva") && cuenta.getTipoCuenta().equals("individual")) {
                                            if(solicitudActivacion) {   //Ya se ha enviado la solicitud de activacion
                                    %>
                                    <p>Solicitud Enviada</p>
                                    <%
                                    } else {
                                    %>
                                    <form action="/empresa/solicitarActivacion" method="post">
                                        <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>" />
                                        <button>Solicitar Activacion</button>
                                    </form>
                                    <%
                                        }
                                    } else if(cuenta.getEstadoCuenta().equals("bloqueada") && cuenta.getTipoCuenta().equals("individual")) {
                                        if(solicitudDesbloqueo) {
                                    %>
                                    <p>Solicitud Enviada</p>
                                    <%
                                    } else {
                                    %>
                                    <form action="/empresa/solicitarDesbloqueo" method="post">
                                        <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>" />
                                        <button>Solicitar Desbloqueo</button>
                                    </form>
                                    <%
                                            }
                                        }
                                    %>
                                </td>
                                <td style="text-align: center"><%= cuenta.getTipoCuenta() %></td>
                                <td style="text-align: center"><%= cuenta.getMoneda() %></td>
                                <td>
                                    <form:form method="post" action="/empresa/cambioDivisa" modelAttribute="cambioDivisa">
                                        <form:hidden path="cuentaOrigen" value="<%= cuenta.getNumeroCuenta() %>" />
                                        <form:hidden path="cuentaDestino" value="<%= cuenta.getNumeroCuenta() %>" />
                                        <form:hidden path="timeStamp" value="<%= new java.sql.Timestamp(System.currentTimeMillis()) %>" />
                                        <form:hidden path="importeOrigen" value="<%= cuenta.getDinero() %>" />
                                        <form:hidden path="importeDestino" />
                                        <form:hidden path="tipo" />
                                        <form:hidden path="divisaCuentaOrigen" value="<%= cuenta.getMoneda() %>" />
                                        <form:select path="divisaCuentaDestino">
                                            <form:options items="${divisas}" itemLabel="moneda" itemValue="moneda" />
                                        </form:select>
                                        <form method="post" action="/empresa/cambioDivisa">
                                            <input type="hidden" name="idAsociado" value="<%= cliente.getId() %>">
                                            <button class="btn btn-dark" style="margin-bottom: 8px" >Cambiar la divisa</button>
                                        </form>
                                    </form:form>
                                </td>
                                <td>
                                    <form action="/cliente/<%=cliente.getId()%>" method="get">
                                        <input type="hidden" name="id" value="<%= cliente.getId() %>">
                                        <button class="btn btn4" style="border: 2px solid #ffd368; width: 200px" type="submit">Ver movimientos</button>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            <%
                }
            %>

            <br/>
        </div>

    </div>
</div>
<hr>
<div class="container">
    <div class="row">
        <div class="col-5">
            <h1>Listado de todos los socios y autorizados</h1>

            <form:form action="/empresa/filtrar" method="post" modelAttribute="filtro">
                <form:hidden path="nif" />
                <form:hidden path="primerNombre" />
                <form:hidden path="primerApellido" />
            Buscar por nombre de la empresa: <form:input path="nombreEmpresa" /> <br/>
            <form action="" method="post">
                <input type="hidden" name="id" value="<%= cliente.getId() %>">
                <button class="btn btn-dark">Buscar</button>
            </form>
            </form:form>

            <table class="table" style="border-color: black">
                <tr>
                    <th style="text-align: center">NIF</th>
                    <th style="text-align: center">Nombre</th>
                    <th style="text-align: center">Empresa</th>
                    <th style="text-align: center">Puesto</th>
                </tr>
                <%
                    for (Cliente asociado : listaAsociados) {
                %>
                <tr>
                    <td style="text-align: center"><%= asociado.getNif() %></td>
                    <td style="text-align: center"><%= asociado.getPrimerNombre() %></td>
                    <td style="text-align: center">
                        <%
                            boolean encontrado = false;
                            int i = 0;
                            String nombreEmpresa = "";
                            String cif = "";
                            while(!encontrado) {
                                if(empresas.get(i).getIdEmpresa() == asociado.getIdEmpresa()) {
                                    encontrado = true;
                                    nombreEmpresa = empresas.get(i).getNombre();
                                    cif = empresas.get(i).getCif();
                                }
                                i++;
                            }
                        %>
                        <%= nombreEmpresa %>
                    </td>
                    <td style="text-align: center"><%= asociado.getTipo() %></td>
                </tr>
                <%
                    }
                %>

            </table>

        </div>
        <div class="col-2"></div>
        <div class="col-5" style="margin-top: 50px">
            <form action="/empresa/miPerfil" method="get">
                <input type="hidden" name="id" value="<%= cliente.getId() %>">
                <button class="btn btn1" style="border: 2px solid #00c1ff; width: 400px" type="submit">Modificar mis datos</button>
            </form>
            <form action="/empresa/editarEmpresa" method="get">
                <input type="hidden" name="id" value="<%= cliente.getId()%>">
                <button class="btn btn1" style="border: 2px solid #00c1ff; width: 400px" type="submit">Modificar datos de la empresa</button>
            </form>
            <form action="/empresa/transferencia" method="get">
                <input type="hidden" name="id" value="<%= cliente.getId() %>">
                <button class="btn btn2" style="border: 2px solid #00e0ac; width: 400px" type="submit">Realizar transferencia</button>
            </form>
            <form action="/empresa/miEmpresa" method="get">
                <input type="hidden" name="id" value="<%= cliente.getId() %>">
                <button class="btn btn2" style="border: 2px solid #00e0ac; width: 400px" type="submit">Ver listado de socios/autorizados de mi empresa</button>
            </form>

            <%
                if (chat == null) {
            %>
            <form action="/asistente/abrirChatConAsistente/<%=cliente.getId()%>" method="get">
                <button class="btn btn3" style="border: 2px solid #b6ec5a; width: 400px" type="submit">Abrir nuevo chat con asistente</button>
            </form>
            <%
            } else {
            %>
            <form action="/asistente/chatCliente/<%=chat.getId()%>" method="get">
                <button class="btn btn3" style="border: 2px solid #b6ec5a; width: 400px" type="submit">Acceder a chat con asistente</button>
            </form>
            <%
                }
            %>
            <form action="/cajero/<%= cliente.getId() %>" method="get">
                <button class="btn btn3" style="border: 2px solid #b6ec5a; width: 400px" type="submit">Acceder al cajero</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>