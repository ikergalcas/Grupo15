package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hecho por: Álvaro Yuste Moreno 80%
 * Carla Serracant Guevara 20%
 */
@Service
public class EmpresaService {
    @Autowired
    protected SolicitudRepository solicitudRepository;
    @Autowired
    protected GestorRepository gestorRepository;
    @Autowired
    protected EstadoSolicitudRepository estadoSolicitudRepository;
    @Autowired
    protected TipoSolicitudRepository tipoSolicitudRepository;
    @Autowired
    protected DivisaRepository divisaRepository;
    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;
    @Autowired
    protected CambioDivisaRepository cambioDivisaRepository;
    @Autowired
    protected AsociadoRepository asociadoRepository;
    @Autowired
    protected EstadoCuentaRepository estadoCuentaRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected EmpresaRepository empresaRepository;
    @Autowired
    protected MovimientoRepository movimientoRepository;
    @Autowired
    protected TipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    protected RolClienteRepository rolClienteRepository;

    public Cliente buscarCliente(Integer id) {
        ClienteEntity asociadoEntity = this.asociadoRepository.findById(id).orElse(null);
        if(asociadoEntity != null) {
            return asociadoEntity.toDTO();
        } else {
            return null;
        }
    }

    public Empresa buscarEmpresa(Integer id) {
        EmpresaEntity empresaEntity = this.empresaRepository.findById(id).orElse(null);
        if(empresaEntity != null) {
            return empresaEntity.toDTO();
        } else {
            return null;
        }
    }

    public Cuenta buscarCuentaPorIBAN(String iban) {
        CuentaEntity cuentaEntity = this.cuentaRepository.buscarCuentaPorNumeroCuenta(iban);
        if(cuentaEntity == null) {
            return null;
        } else {
            return cuentaEntity.toDTO();
        }
    }

    public Cuenta buscarCuentaEmpresa(List<Cuenta> cuentaList) {
        Cuenta cuenta = null;
        for(Cuenta c : cuentaList) {
            if(c.getTipoCuenta().equals("empresa")) {
                cuenta = c;
            }
        }
        return cuenta;
    }


    /**
     * En este metodo miro si la cuenta individual del asociado esta activa y si esta bloqueado o no de la empresa
     */
    public List<Cuenta> buscarCuentasAsociadoActivas(Integer id) {
        //Hago este cambio de CuentaClienteEntity -> CuentaEntity porque en CuentaRepository no me deja hacer un query
        //Buscando cuentas por el id del cliente. Supongo que sera por la tabla intermedia (CuentaClienteEntity)
        List<CuentaClienteEntity> cuentaClienteList = this.cuentaClienteRepository.buscarCuentasActivas(id);
        List<CuentaEntity> cuentaList = new ArrayList<>();
        ClienteEntity asociado = this.asociadoRepository.findById(id).orElse(null);
        boolean bloqueado = asociado.getAcceso() == 0;

        //Paso la lista al tipo CuentaEntity
        for(CuentaClienteEntity cuentaCliente : cuentaClienteList) {
            if(bloqueado && cuentaCliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId().getTipo().equals("empresa")) {
            } else {
                cuentaList.add(cuentaCliente.getCuentaByCuentaId());
            }
        }

        return listaCuentasADTO(cuentaList);
    }

    public List<Cuenta> buscarCuentasAsociado(Integer id) {
        List<CuentaClienteEntity> cuentaClienteList = this.cuentaClienteRepository.buscarCuentasCliente(id);
        List<CuentaEntity> cuentaList = new ArrayList<>();

        //Paso la lista al tipo CuentaEntity
        for(CuentaClienteEntity cuentaCliente : cuentaClienteList) {
            cuentaList.add(cuentaCliente.getCuentaByCuentaId());
        }

        return listaCuentasADTO(cuentaList);
    }

    protected List<Cuenta> listaCuentasADTO(List<CuentaEntity> listaEntity) {
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for(CuentaEntity entity : listaEntity) {
            cuentasDTO.add(entity.toDTO());
        }
        return cuentasDTO;
    }

    public List<Empresa> listarEmpresas() {
        List<EmpresaEntity> empresaEntities = this.empresaRepository.findAll();

        return listaEmpresasADTO(empresaEntities);
    }

    protected List<Empresa> listaEmpresasADTO(List<EmpresaEntity> entities) {
        List<Empresa> empresas = new ArrayList<>();

        for(EmpresaEntity entity : entities) {
            empresas.add(entity.toDTO());
        }

        return empresas;
    }

    public List<Cliente> listarAsociados() {
        List<ClienteEntity> asociados = this.asociadoRepository.buscarPorTipoEmpresa();

        return listaAsociadosADTO(filtrarPorDadosDeAlta(asociados));
    }

    public List<Cliente> listarAsociados(String nombreEmpresa) {
        List<ClienteEntity> asociados  = new ArrayList<>();
        if(nombreEmpresa.isEmpty()) {
            asociados = this.asociadoRepository.buscarPorTipoEmpresa();
        } else {
            asociados = this.asociadoRepository.buscarPorEmpresa(nombreEmpresa);
        }
        return listaAsociadosADTO(filtrarPorDadosDeAlta(asociados));
    }


    public List<Cliente> listarAsociadosDeMiEmpresa(Integer id) {
        List<ClienteEntity> asociados = this.asociadoRepository.buscarSociosAutorizadosDeMiEmpresa(id);

        return listaAsociadosADTO(filtrarPorDadosDeAlta(asociados));
    }

    /**
     * En este metodo filtro la lista dada de tal manera que solo me quedo con los que tienen la solicitud
     * de alta de individual o empresa aceptada. Uso este metodo para "listarAsociados" y "listarAsociadosDeMiEmpresa"
     */
    protected List<ClienteEntity> filtrarPorDadosDeAlta(List<ClienteEntity> lista) {
        List<ClienteEntity> filtrado = new ArrayList<>();

        for(ClienteEntity entity : lista) {
            if(buscarSolicitudAltaPendiente(entity.getId()) == null) {
                filtrado.add(entity);
            }
        }

        return filtrado;
    }

    /**
     * En este metodo busco si el cliente pasado como parametro tiene alguna solicitud de alta_empresa o alta_inidvidual
     * pendiente
     */
    protected SolicitudEntity buscarSolicitudAltaPendiente(Integer idAsociado) {
        SolicitudEntity solicitud = this.solicitudRepository.buscarSolicitudAltaClientePorIdCliente(idAsociado);
        if(solicitud == null) { solicitud = this.solicitudRepository.buscarSolicitudAltaEmpresaPorIdCliente(idAsociado); }
        return solicitud;
    }

    public Cliente buscarAsociadoPorNif(Integer idEmpresa, String nif) {
        Cliente cliente = new Cliente();
        ClienteEntity entity = this.asociadoRepository.buscarPorNif(idEmpresa, nif);
        if(entity != null) {
            return entity.toDTO();
        } else {
            return null;
        }
    }

    public List<Cliente> listarAsociadosDeMiEmpresa(Integer idEmpresa, String primerNombre, String primerApellido, String puesto) {
        List<ClienteEntity> asociados = null;
        if(primerApellido.isEmpty() && puesto.isEmpty()) { //Busco por primer nombre
            asociados = this.asociadoRepository.buscarPorPrimerNombre(idEmpresa, primerNombre);
        } else if(primerNombre.isEmpty() && puesto.isEmpty()) {    //Busco por primer apellido
            asociados = this.asociadoRepository.buscarPorPrimerApellido(idEmpresa, primerApellido);
        } else if(primerNombre.isEmpty() && primerApellido.isEmpty()) {    //Busco por puesto
          asociados = this.asociadoRepository.buscarPorPuesto(idEmpresa, puesto);
        } else if(puesto.isEmpty()) {   //Busco por primer nombre y primer apellido
            asociados = this.asociadoRepository.buscarPorPrimerNombreyPrimerApellido(idEmpresa, primerNombre, primerApellido);
        } else if(primerApellido.isEmpty()) {   //Busco por primer nombre y puesto
            asociados = this.asociadoRepository.buscarPorPrimerNombreyPuesto(idEmpresa, primerNombre, puesto);
        } else if(primerNombre.isEmpty()) {     //Busco por primer apellido y puesto
            asociados = this.asociadoRepository.buscarPorPrimerApellidoyPuesto(idEmpresa, primerApellido, puesto);
        } else {  //Buscar por todo
            asociados = this.asociadoRepository.buscarPorTodo(idEmpresa, primerNombre, primerApellido, puesto);
        }

        return listaAsociadosADTO(filtrarPorDadosDeAlta(asociados));
    }

    protected List<Cliente> listaAsociadosADTO(List<ClienteEntity> listaEntity) {
        List<Cliente> listaDTO = new ArrayList<>();
        listaEntity.forEach((ClienteEntity entity) -> listaDTO.add(entity.toDTO()));
        return listaDTO;
    }


    //Ahora mismo este guardar solo es para el editar asi que aprovecho para cogerlo de la bd
    //y sobrescribo los datos editables solo. El resto (cuentas, empresa,...) se quedan como estaban en la bd
    public void guardarAsociado(Cliente editado) {
        ClienteEntity cliente = this.asociadoRepository.findById(editado.getId()).orElse(null);

        cliente.setId(editado.getId());
        cliente.setNif(editado.getNif());
        cliente.setPrimerNombre(editado.getPrimerNombre());
        cliente.setSegundoNombre(editado.getSegundoNombre());
        cliente.setPrimerApellido(editado.getPrimerApellido());
        cliente.setSegundoApellido(editado.getSegundoApellido());
        cliente.setFechaNacimiento(editado.getFechaNacimiento());
        cliente.setCalle(editado.getCalle());
        cliente.setNumero(editado.getNumero());
        cliente.setPuerta(editado.getPuerta());
        cliente.setCiudad(editado.getCiudad());
        cliente.setPais(editado.getPais());
        cliente.setRegion(editado.getRegion());
        cliente.setCp(editado.getCp());

        this.asociadoRepository.save(cliente);
    }

    public void guardarEmpresa(Empresa empresa) {
        EmpresaEntity empresaEntity = this.empresaRepository.findById(empresa.getIdEmpresa()).orElse(null);

        //Hago set de todos los atributos que son editables
        empresaEntity.setNombre(empresa.getNombre());
        empresaEntity.setCif(empresa.getCif());
        empresaEntity.setContrasena(empresa.getContrasenaEmpresa());

        this.empresaRepository.save(empresaEntity);
    }

    /**
     * En este método hago todo el proceso de la transferencia.
     * 1.- Compruebo si hay algún error.
     * 2.- Compruebo si la cuenta destino es de una divisa distinta a la cuenta origen (en ese caso hago otro movimiento extra
     *     que será el cambio de divisa).
     * 3.- Asigno todos los atributos recogidos en el objeto transferencia del formulario
     * 4.- Esta función devolverá una cadena de caracteres que si no es vacía indicará la causa del error de la transferencia
     */
    public String transferencia(Movimiento transferencia, Integer idAsociado) {
        ClienteEntity asociado = this.asociadoRepository.findById(idAsociado).orElse(null);
        MovimientoEntity movimiento = new MovimientoEntity();
        String error = "";

        //Primero pillo las cuentas y miro si no hay errores en los datos
        CuentaEntity cuentaOrigen = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaOrigen());
        CuentaEntity cuentaDestino = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaDestino());

        //Compruebo si la cuenta destino y la cuenta origen son la misma
        if(cuentaDestino.equals(cuentaOrigen)) {
            error = "coinciden";
        } else if(transferencia.getImporteOrigen() <= 0) {  //El dinero de la transferencia debe de ser positivo
            error = "importeIncorrecto";
        } else if(cuentaOrigen.getDinero() < transferencia.getImporteOrigen()) {    //Miro si hay suficiente dinero en la cuenta origen
            error = "dineroInsuficiente";
        } else {
            TipoMovimientoEntity tipoMov = this.tipoMovimientoRepository.buscarTipoTransferencia();
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMov);

            Date fecha = new Date();
            Timestamp timestamp = new Timestamp(fecha.getTime());
            movimiento.setTimeStamp(timestamp);

            movimiento.setCuentaByCuentaDestinoId(cuentaDestino);
            movimiento.setCuentaByCuentaOrigenId(cuentaOrigen);
            movimiento.setDivisaByMonedaOrigenId(cuentaOrigen.getDivisaByDivisaId());
            movimiento.setDivisaByMonedaDestinoId(cuentaDestino.getDivisaByDivisaId());
            movimiento.setClienteByClienteId(asociado);

            //La transferencia es sin cambio de divisa
            if(cuentaOrigen.getDivisaByDivisaId().getMoneda().equals(cuentaDestino.getDivisaByDivisaId().getMoneda())) {
                movimiento.setImporteDestino(transferencia.getImporteOrigen());
                movimiento.setImporteOrigen(transferencia.getImporteOrigen());
                cuentaOrigen.setDinero(cuentaOrigen.getDinero() - transferencia.getImporteOrigen());
                cuentaDestino.setDinero(cuentaDestino.getDinero() + transferencia.getImporteOrigen());
            } else {    //La transferencia es con cambio de divisa
                movimiento.setImporteOrigen(transferencia.getImporteOrigen());

                //Aplico el cambio de cantidad de una moneda a otra y redondeo a 3 decimales
                CambioDivisaEntity cambio = this.cambioDivisaRepository.buscarCambioDivisa(cuentaOrigen.getDivisaByDivisaId().getMoneda(), cuentaDestino.getDivisaByDivisaId().getMoneda());
                Double dineroMonedaDestino = transferencia.getImporteOrigen() * cambio.getCambio();
                DecimalFormat formato = new DecimalFormat("#.###");
                String redondeo = formato.format(dineroMonedaDestino).replace(",",".");
                Double dineroMonedaDestinoRedondeado = Double.parseDouble(redondeo);
                movimiento.setImporteDestino(dineroMonedaDestinoRedondeado);

                cuentaOrigen.setDinero(cuentaOrigen.getDinero() - transferencia.getImporteOrigen());
                cuentaDestino.setDinero(cuentaDestino.getDinero() + dineroMonedaDestinoRedondeado);

                //En este caso hago también un movimiento por el cambio de divisa
                MovimientoEntity movimientoCambioDivisa = new MovimientoEntity();
                TipoMovimientoEntity tipoMov2 = this.tipoMovimientoRepository.buscarTipoCambioDivisa();
                movimientoCambioDivisa.setTipoMovimientoByTipoMovimientoId(tipoMov2);
                movimientoCambioDivisa.setImporteOrigen(transferencia.getImporteOrigen());
                movimientoCambioDivisa.setImporteDestino(dineroMonedaDestino);
                movimientoCambioDivisa.setTimeStamp(timestamp);
                movimientoCambioDivisa.setCuentaByCuentaDestinoId(cuentaDestino);
                movimientoCambioDivisa.setCuentaByCuentaOrigenId(cuentaOrigen);
                movimientoCambioDivisa.setDivisaByMonedaOrigenId(cuentaOrigen.getDivisaByDivisaId());
                movimientoCambioDivisa.setDivisaByMonedaDestinoId(cuentaDestino.getDivisaByDivisaId());
                movimientoCambioDivisa.setClienteByClienteId(asociado);

                this.movimientoRepository.save(movimientoCambioDivisa);
            }

            this.cuentaRepository.save(cuentaOrigen);
            this.cuentaRepository.save(cuentaDestino);
            this.movimientoRepository.save(movimiento);
        }
        return error;
    }

    /**
     * En este método busco todas las cuentas que estén activas para la cuentaDestino de la transferencia.
     */
    public List<Cuenta> buscarCuentasTransferencia(List<Cuenta> cuentas) {
        List<CuentaEntity> cuentasDestino = this.cuentaRepository.buscarCuentasTransferencia();

        return listaCuentasADTO(cuentasDestino);
    }

    public List<Divisa> buscarDivisas() {
        List<DivisaEntity> divisaEntities = this.divisaRepository.findAll();

        return divisasADTO(divisaEntities);
    }

    private List<Divisa> divisasADTO(List<DivisaEntity> divisaEntities) {
        List<Divisa> divisas = new ArrayList<>();

        for(DivisaEntity entity : divisaEntities) {
            divisas.add(entity.toDTO());
        }

        return divisas;
    }

    public void cambioDivisa(Movimiento divisa, Integer idAsociado) {
        MovimientoEntity movimiento = new MovimientoEntity();

        ClienteEntity asociado = this.asociadoRepository.findById(idAsociado).orElse(null);
        TipoMovimientoEntity tipoMov = this.tipoMovimientoRepository.buscarTipoCambioDivisa();
        CuentaEntity cuenta = this.cuentaRepository.buscarCuentaPorNumeroCuenta(divisa.getCuentaOrigen());
        DivisaEntity divisaNueva = this.divisaRepository.buscarDivisaPorNombre(divisa.getDivisaCuentaDestino());
        DivisaEntity divisaOriginal = this.divisaRepository.buscarDivisaPorNombre(divisa.getDivisaCuentaOrigen());
        CambioDivisaEntity cambio = this.cambioDivisaRepository.buscarCambioDivisa(divisa.getDivisaCuentaOrigen(), divisa.getDivisaCuentaDestino());

        //La mayoria de los atributos de movimiento los cojo del objeto divisa que lo he rellenado en el jsp
        movimiento.setTimeStamp(divisa.getTimeStamp());
        movimiento.setCuentaByCuentaOrigenId(cuenta);
        movimiento.setCuentaByCuentaDestinoId(cuenta);
        movimiento.setTipoMovimientoByTipoMovimientoId(tipoMov);

        //Para redondear uso DecimalFormat y cambio las comas por puntos usando el replace
        Double dineroMonedaNueva = cuenta.getDinero() * cambio.getCambio();
        DecimalFormat formato = new DecimalFormat("#.###");
        String redondeo = formato.format(dineroMonedaNueva).replace(",",".");
        Double dineroMonedaDestinoRedondeado = Double.parseDouble(redondeo);

        movimiento.setImporteOrigen(cuenta.getDinero());
        movimiento.setImporteDestino(dineroMonedaDestinoRedondeado);
        movimiento.setDivisaByMonedaOrigenId(divisaOriginal);
        movimiento.setDivisaByMonedaDestinoId(divisaNueva);
        movimiento.setClienteByClienteId(asociado);
        this.movimientoRepository.save(movimiento);

        cuenta.setDivisaByDivisaId(divisaNueva);
        cuenta.setDinero(dineroMonedaDestinoRedondeado);
        this.cuentaRepository.save(cuenta);
    }

    /**
     * Controlo el acceso con un entero -> 0 = acceso denegado | 1 = acceso permitido
     */
    public void bloquear(Integer idBloq) {
        ClienteEntity cliente = this.asociadoRepository.findById(idBloq).orElse(null);
        cliente.setAcceso(0);

        this.asociadoRepository.save(cliente);
    }

    public void desbloquear(Integer idBloq) {
        ClienteEntity cliente = this.asociadoRepository.findById(idBloq).orElse(null);
        cliente.setAcceso(1);

        this.asociadoRepository.save(cliente);
    }

    public Cuenta buscarCuentaPorId(Integer idCuenta) {
        CuentaEntity entity = this.cuentaRepository.findById(idCuenta).orElse(null);

        return entity.toDTO();
    }

    public List<Movimiento> listarMovimientosCuenta(Integer idCuenta) {
        List<MovimientoEntity> movimientos = this.movimientoRepository.buscarMovimientosPorIdCuenta(idCuenta);

        return movimientosADTO(movimientos);
    }

    private List<Movimiento> movimientosADTO(List<MovimientoEntity> movimientosEntities) {
        List<Movimiento> movimientos = new ArrayList<>();

        for(MovimientoEntity entity : movimientosEntities) {
            movimientos.add(entity.toDTO());
        }

        return movimientos;
    }

    /**
     * Con estos métodos creo la solicitud de tipo desbloqueo_individual/activa_individual y las asocio al gestor menos
     * ocupado. Además una vez que envio la solicitud ya no saldrá más el botón de solicitar. De eso me encargo en el jsp
     * "inicio"
     */
    public void solicitarDesbloqueo(Integer idAsociado) {
        ClienteEntity cliente = this.asociadoRepository.findById(idAsociado).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoDesbloqueo = this.tipoSolicitudRepository.buscarTipoDesbloqueoIndividual();
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoDesbloqueo);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);

    }

    public void solicitarActivacion(Integer idAsociado) {
        ClienteEntity cliente = this.asociadoRepository.findById(idAsociado).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoActivacion = this.tipoSolicitudRepository.buscarTipoActivacionIndividual();
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoActivacion);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);

    }

    /**
     * Uso este método auxiliar para buscar el gestor con menos solicitudes pendientes y asignarle a este la solicitud de
     * desbloqueo/activacion
     */
    protected EmpleadoEntity buscarGestorMenosOcupado() {
        List<EmpleadoEntity> gestores = this.gestorRepository.buscarGestores();

        int min = Integer.MAX_VALUE;
        EmpleadoEntity gestorElegido = null;
        for(EmpleadoEntity gestor : gestores) {
            List<SolicitudEntity> solicitudes = this.solicitudRepository.buscarSolicitudesPendientesDeUnGestor(gestor.getId());
            if(solicitudes.size() < min) {
                min = solicitudes.size();
                gestorElegido = gestor;
            }
        }

        return gestorElegido;
    }

    /**
     * En estos métodos busco si el cliente con el id esta asociado a una solicitud de estado pendiente y del tipo correspondiente
     * (activa_individual o desbloqueo_individual)
     */
    public boolean comprobarSolicitudDesbloqueoEnviada(Integer id) {
        List<SolicitudEntity> solicitudesCliente = this.solicitudRepository.buscarSolicitudesPendientesPorClienteTipoDesbloqueoIndividual(id);

        return solicitudesCliente.size() > 0;
    }

    public boolean comprobarSolicitudActivacionEnviada(Integer id) {
        List<SolicitudEntity> solicitudesCliente = this.solicitudRepository.buscarSolicitudesPendientesPorClienteTipoActivacionIndividual(id);

        return solicitudesCliente.size() > 0;
    }

    /**
     * Esta es la funcion que hace el registro del formulario que esta en el jsp "empresa"
     */
    public void registrarAsociado(Cliente asociado, Integer idCuenta) {
        ClienteEntity entity = new ClienteEntity();
        EmpresaEntity empresaEntity = this.empresaRepository.findById(asociado.getIdEmpresa()).orElse(null);
        RolClienteEntity rol = this.rolClienteRepository.buscarRol(asociado.getTipo());
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);    //cuenta es la cuenta de la empresa
        SolicitudEntity solicitud = new SolicitudEntity();
        TipoSolicitudEntity tipoAlta = this.tipoSolicitudRepository.buscarTipoAltaIndividual();
        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();

        /**
         * Primero asigno a entity (tipo ClienteEntity) los datos dados en el formulario sin relacionarlo con la cuenta de la empresa
         * ya que aún no le puedo asignar el atributo CuentaEntity (tabla intermedia entre cliente y cuenta) porque no se ha subido a la BD.
         * Y viceversa, no puedo asignarle el cliente al objeto cuentaClienteEmpresa hasta que este este subido en la bd
         */
        entity.setNif(asociado.getNif());
        entity.setPrimerNombre(asociado.getPrimerNombre());
        entity.setSegundoNombre(asociado.getSegundoNombre());
        entity.setPrimerApellido(asociado.getPrimerApellido());
        entity.setSegundoApellido(asociado.getSegundoApellido());
        entity.setFechaNacimiento(asociado.getFechaNacimiento());
        entity.setCalle(asociado.getCalle());
        entity.setNumero(asociado.getNumero());
        entity.setPuerta(asociado.getPuerta());
        entity.setCiudad(asociado.getCiudad());
        entity.setPais(asociado.getPais());
        entity.setCp(asociado.getCp());
        entity.setContrasena(asociado.getContrasena());
        entity.setAcceso(asociado.getAcceso());
        entity.setEmpresaByEmpresaId(empresaEntity);
        entity.setRolClienteByRolclienteId(rol);
        this.asociadoRepository.save(entity);

        //Obtengo el cliente que acabo de subir a la base de datos
        ClienteEntity clienteBD = this.asociadoRepository.buscarPorNifRegistroAsociado(asociado.getIdEmpresa(), asociado.getNif());

        //Creo el objeto cuentaClienteEmpresa que servirá para relacionar el asociado con la cuenta de la empresa
        CuentaClienteEntity cuentaClienteEmpresa = new CuentaClienteEntity();
        cuentaClienteEmpresa.setClienteByClienteId(clienteBD);
        cuentaClienteEmpresa.setCuentaByCuentaId(cuenta);
        this.cuentaClienteRepository.save(cuentaClienteEmpresa);

        //Obtengo el objeto cuentaClienteEmpresa para poder asociarlo con el cliente
        CuentaClienteEntity cuentaClienteBD = this.cuentaClienteRepository.buscarPorClienteYCuenta(clienteBD.getId(), cuenta.getId());

        //Creo la relacion entre el asociado y la cuenta de la empresa gracias al objeto cuentaClienteEmpresa
        List<CuentaClienteEntity> cuentaClienteEntities1 = cuenta.getCuentaClientesById();
        cuentaClienteEntities1.add(cuentaClienteBD);
        cuenta.setCuentaClientesById(cuentaClienteEntities1);
        this.cuentaRepository.save(cuenta);

        List<CuentaClienteEntity> cuentaClienteEntities2 = new ArrayList<>();
        cuentaClienteEntities2.add(cuentaClienteBD);
        clienteBD.setCuentaClientesById(cuentaClienteEntities2);
        this.asociadoRepository.save(clienteBD);

        /**
         * Creo la solicitud de alta individual y la relaciono con el nuevo asociado y con el gestor menos ocupado
         */
        solicitud.setClienteByClienteId(clienteBD);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoAlta);
        this.solicitudRepository.save(solicitud);
    }


    /**
     * Filtro para los movimientos de la cuenta de la empresa:
     * - Filtro por el tipo de movimiento, cuenta destino, divisa origen y divisa destino.
     * - Ordeno por el importe origen y por el importe destino. Tanto de manera ascendente como descendente. En la lista de String "opciones"
     *   almaceno el criterio para ordenar seleccionado (importe origen, importe destibo o ambos). Y en la cadena orden almaceno si es ascendente o descendente.
     */
    public List<Movimiento> buscarMovimientosAsociado(Integer id, Integer idCuenta, String tipo, String cuentaDestino, String divisaOrigen, String divisaDestino, Double importeOrigen, Double importeDestino, List<String> opciones, String orden) {
        List<MovimientoEntity> entities = new ArrayList<>();

        //Filtro vacio
        if(tipo.isEmpty() && cuentaDestino.isEmpty() && divisaDestino.isEmpty() && divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociado(id, idCuenta);

        } else if(cuentaDestino.isEmpty() && divisaDestino.isEmpty() && divisaOrigen.isEmpty()){
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipo(id, idCuenta, tipo);    //Buscar por tipo

        } else if(tipo.isEmpty() && divisaDestino.isEmpty() && divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorCuentaDestino(id, idCuenta, cuentaDestino);  //Buscar por Cuenta Destino

        } else if(tipo.isEmpty() && cuentaDestino.isEmpty() && divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorDivisaDestino(id, idCuenta, divisaDestino);  //Buscar por Divisa Destino

        } else if(tipo.isEmpty() && cuentaDestino.isEmpty() && divisaDestino.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorDivisaOrigen(id, idCuenta, divisaOrigen);    //Buscar por Divisa Origen

        } else if(divisaDestino.isEmpty() && divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoYCuentaDestino(id, idCuenta, tipo, cuentaDestino);  //Busco por Tipo y Cuenta Destino

        } else if(cuentaDestino.isEmpty() && divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoYDivisaDestino(id, idCuenta, tipo, divisaDestino);   //Busco por Tipo y Divisa Destino

        } else if(cuentaDestino.isEmpty() && divisaDestino.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoYDivisaOrigen(id, idCuenta, tipo, divisaOrigen);   //Busco por Tipo y Divisa Origen

        } else if(tipo.isEmpty() && divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorCuentaDestinoYDivisaDestino(id, idCuenta, cuentaDestino, divisaDestino); //Busco por Cuenta Destino y Divisa Destino

        } else if(tipo.isEmpty() && divisaDestino.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorCuentaDestinoYDivisaOrigen(id, idCuenta, cuentaDestino, divisaDestino);  //Busco por Cuenta Destino y Divisa Origen

        } else if(tipo.isEmpty() && cuentaDestino.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorDivisaDestinoYDivisaOrigen(id, idCuenta, divisaDestino, divisaOrigen);   //Busco por Divisa Destino y Divisa Origen

        } else if(divisaOrigen.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoCuentaDestinoDivisaDestino(id, idCuenta, tipo, cuentaDestino, divisaDestino);    //Busco port Tipo, Cuenta Destino y Divisa Destino

        } else if(divisaDestino.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoCuentaDestinoDivisaOrigen(id, idCuenta, tipo, cuentaDestino, divisaOrigen);      //Buscar por Tipo, Cuenta Destino y Divisa Origen

        } else if(cuentaDestino.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoDivisaDestinoDivisaOrigen(id, idCuenta, tipo, divisaDestino, divisaOrigen);      //Buscar por Tipo, Divisa Destino y Divisa Origen

        } else if(tipo.isEmpty()) {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorCuentaDestinoDivisaDestinoDivisaOrigen(id, idCuenta, cuentaDestino, divisaDestino, divisaOrigen);      //Buscar por Cuenta Destino, Divisa Destino y Divisa Origen

        } else {
            entities = this.movimientoRepository.buscarMovimientosAsociadoPorTipoCuentaDestinoDivisaDestinoDivisaOrigen(id, idCuenta, tipo, cuentaDestino, divisaDestino, divisaOrigen); //Buscar por Tipo, Cuenta Destino, Divisa Destino y Divisa Origen
        }

        //Ahora que tengo la lista la ordeno según el criterio dado por el filtro
        if(opciones != null && !opciones.isEmpty()) {
            if(orden.equals("ascendente")) {    //Orden ascendente
                if(opciones.contains("origen") && opciones.contains("destino") && opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenDestinoYFechaAscendente(entities);
                } else if(opciones.contains("origen") && opciones.contains("destino")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenYDestinoAscendente(entities);
                } else if(opciones.contains("origen") && opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenYFechaAscendente(entities);
                } else if(opciones.contains("destino") && opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorImporteDestinoYFechaAscendente(entities);
                } else if(opciones.contains("origen")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenAscendente(entities);
                } else if(opciones.contains("destino")) {
                    entities = this.movimientoRepository.ordenarPorImporteDestinoAscendente(entities);
                } else if(opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorFechaAscendente(entities);
                }
            } else {    //Orden descendente
                if(opciones.contains("origen") && opciones.contains("destino") && opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenDestinoYFechaDescendente(entities);
                } else if(opciones.contains("origen") && opciones.contains("destino")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenYDestinoDescendente(entities);
                } else if(opciones.contains("origen") && opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenYFechaDescendente(entities);
                } else if(opciones.contains("destino") && opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorImporteDestinoYFechaDescendente(entities);
                } else if(opciones.contains("origen")) {
                    entities = this.movimientoRepository.ordenarPorImporteOrigenDescendente(entities);
                } else if(opciones.contains("destino")) {
                    entities = this.movimientoRepository.ordenarPorImporteDestinoDescendente(entities);
                } else if(opciones.contains("fecha")) {
                    entities = this.movimientoRepository.ordenarPorFechaDescendente(entities);
                }
            }
        }

        return listaMovimientosADTO(entities);
    }

    protected List<Movimiento> listaMovimientosADTO(List<MovimientoEntity> lista) {
        List<Movimiento> dto = new ArrayList<>();
        lista.forEach((MovimientoEntity entity) -> dto.add(entity.toDTO()));
        return dto;
    }

    public Empresa buscarEmpresaAPartirDeCliente(Cliente cliente) {
        /* Carla Serracant Guevara */
        EmpresaEntity empresa = empresaRepository.findById(cliente.getIdEmpresa()).orElse(null);

        return empresa.toDTO();
    }

    public List<Empresa> buscarEmpresas() {
        /* Carla Serracant Guevara */
        List<EmpresaEntity> empresas = empresaRepository.findAll();
        List<Empresa> empresasDTO = new ArrayList<>();
        for (EmpresaEntity e : empresas) {
            empresasDTO.add(e.toDTO());
        }

        return empresasDTO;
    }

    public List<Empresa> buscarEmpresaPorCif(String cif) {
        /* Carla Serracant Guevara */
        List<EmpresaEntity> empresaEntities = empresaRepository.findByFiltroCif(cif);
        List<Empresa> empresas = new ArrayList<>();
        for (EmpresaEntity e : empresaEntities) {
            empresas.add(e.toDTO());
        }

        return empresas;
    }

    public Empresa buscarEmpresaDevuelveDTO(Integer id) {
        EmpresaEntity empresaEntity = this.empresaRepository.findById(id).orElse(null);
        return empresaEntity.toDTO();
    }
}
