
<%
    /**
     * Hecho por: Álvaro Yuste Moreno
     */

    String error = (String) request.getAttribute("error");
    Integer idAsociado = (Integer) request.getAttribute("idAsociado");
%>

<html>
<head>
    <title>error transferencia</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.0/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1 style="text-align: center; color: #fa3342">Error al hacer la transferencia. </h1>
            <%
                if(error.equals("coinciden")) {
            %>
            <h1 style="text-align: center; color: #fa3342">La cuenta origen y la cuenta destino deben ser distintas</h1>
            <%
            } else if(error.equals("importeIncorrecto")) {
            %>
            <h1 style="text-align: center; color: #fa3342">El importe introducido debe ser mayor de 0</h1>
            <%
            } else if(error.equals("dineroInsuficiente")){
            %>
            <h1 style="text-align: center; color: #fa3342">La cuenta origen no tiene suficiente dinero</h1>
            <%
                }
            %>

            <form action="/empresa/transferencia" method="get" style="display:flex; justify-content:center; margin-top: 100px">
                <input type="hidden" name="id" value="<%= idAsociado %>">
                <button class="btn btn-info" style="width: 320px">Volver a hacer transferencia</button>
            </form>
            <form action="/empresa/" method="get" style="display:flex; justify-content:center;">
                <input type="hidden" name="id" value="<%= idAsociado %>">
                <button class="btn btn-info" style="width: 320px">Ver listado de todos los asociados</button>
            </form>
            <form action="/empresa/miEmpresa" method="get" style="display:flex; justify-content:center;">
                <input type="hidden" name="id" value="<%= idAsociado %>">
                <button class="btn btn-info" style="width: 320px">Ver listado de asociados de mi empresa</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>