package co.gov.movilidadbogota.core.service;


import co.gov.movilidadbogota.core.dao.*;
import co.gov.movilidadbogota.core.dto.*;
import co.gov.movilidadbogota.core.entity.*;
import co.gov.movilidadbogota.core.util.SystemParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilityService {

    @Autowired
    private AfpDAO afpDAO;
    @Autowired
    private EpsDAO epsDAO;
    @Autowired
    private ArlDAO arlDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private TipoDocumentoDAO tipoDocumentoDAO;
    @Autowired
    private ParametroSimurDAO parametroSimurDAO;
    @Autowired
    private TarjetacontrolEstadoDAO tarjetacontrolEstadoDAO;
    @Autowired
    private MetodoCobroDAO metodoCobroDAO;
    @Autowired
    private TipoServicioDAO tipoServicioDAO;
    @Autowired
    private OperadoresPilaDAO operadoresPilaDAO;
    @Autowired
    private ConductorDAO conductorDAO;

    private DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss][.SSS]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();


    public List<EmpresaDTO> getEmpresaDTOS(String usuario){
        List<EmpresaDTO> companyDTOS = usuarioDAO.findCompanysByUserName(usuario);
        Collections.sort(companyDTOS);
        return companyDTOS;
    }

    public ParametroDTO getParametroDTO(String keyParametro){
        return parametroSimurDAO.findByKey(keyParametro);
    }

    public TipoDocumentoEntity getTipoDocumentoEntity(Long idTipoDocumento){
        return tipoDocumentoDAO.findByPrimaryKey(idTipoDocumento);
    }

    public List<MetodoCobroDTO> getMetodoCobroDTOS( ) {
        LocalDateTime finMetodoCobroUnidades = null;

        ParametroDTO fechaFinMetodoCobroUnidades = parametroSimurDAO.findByKey(SystemParameters.FECHA_FIN_METODO_COBRO_UNIDADES.getValue());
        if (fechaFinMetodoCobroUnidades != null && !fechaFinMetodoCobroUnidades.getValorParametro().isEmpty()) {
            finMetodoCobroUnidades = LocalDateTime.parse(fechaFinMetodoCobroUnidades.getValorParametro(), dateTimeFormatter);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        List<MetodoCobroEntity> listMetodoCobroEntity = metodoCobroDAO.findAll();
        List<MetodoCobroDTO> listMetodoCobroDTO = new ArrayList<>();
        MetodoCobroDTO metodoCobroDTO;
        for (MetodoCobroEntity metodoCobroEntity : listMetodoCobroEntity) {
            if (metodoCobroEntity.getIdEstado() == 1) {
                metodoCobroDTO = new MetodoCobroDTO();
                metodoCobroDTO.setDescripcion(metodoCobroEntity.getDescripcion());
                metodoCobroDTO.setIdEstado(metodoCobroEntity.getId());
                metodoCobroDTO.setIdMetodoCobro(metodoCobroEntity.getId());
                listMetodoCobroDTO.add(metodoCobroDTO);
                if (metodoCobroEntity.getId() == 2 && finMetodoCobroUnidades != null && finMetodoCobroUnidades.isBefore(localDateTime)) {
                    listMetodoCobroDTO.remove(metodoCobroDTO);
                }
            }
        }
        Collections.sort(listMetodoCobroDTO);
        return listMetodoCobroDTO;
    }

    public List<StatusControlCardDTO> getStatusControlCardDTOS(){
        List<TarjetacontrolEstadoEntity> statuses = tarjetacontrolEstadoDAO.findAll();
        return statuses.stream().filter(status -> status.isActivo())
                .map(status -> {
                    StatusControlCardDTO statusControlCardDTO = new StatusControlCardDTO();
                    statusControlCardDTO.setDescription(status.getDescripcion());
                    statusControlCardDTO.setId(status.getId());
                    return statusControlCardDTO;
                }).collect(Collectors.toList());
    }

    public List<TipoServicioDTO> getTipoServicioDTOS( ) {
        LocalDateTime inicioTipoServicioLujo = null;
        ParametroDTO fechaInicioNivelServicioLujo = parametroSimurDAO.findByKey(SystemParameters.FECHA_INICIO_NIVEL_SERVICIO_LUJO.getValue());
        if (fechaInicioNivelServicioLujo != null && !fechaInicioNivelServicioLujo.getValorParametro().isEmpty()) {
            inicioTipoServicioLujo = LocalDateTime.parse(fechaInicioNivelServicioLujo.getValorParametro(), dateTimeFormatter);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        List<TipoServicioEntity> listTipoServicioEntity = tipoServicioDAO.findAll();
        List<TipoServicioDTO> listTipoServicioDTO = new ArrayList<>();
        TipoServicioDTO tipoServicioDTO;
        for (TipoServicioEntity tipoServicioEntity : listTipoServicioEntity) {
            if (tipoServicioEntity.getIdEstado() == 1) {
                tipoServicioDTO = new TipoServicioDTO();
                tipoServicioDTO.setDescripcion(tipoServicioEntity.getDescripcion());
                tipoServicioDTO.setIdEstado(tipoServicioEntity.getId());
                tipoServicioDTO.setIdTipoServicio(tipoServicioEntity.getId());
                listTipoServicioDTO.add(tipoServicioDTO);
                if (tipoServicioEntity.getId() == 2 && inicioTipoServicioLujo != null && inicioTipoServicioLujo.isAfter(localDateTime)) {
                    listTipoServicioDTO.remove(tipoServicioDTO);
                }
            }
        }
        Collections.sort(listTipoServicioDTO);
        return listTipoServicioDTO;
    }

    public List<AfpDTO> getAfpDTOS() {
        List<AfpEntity> listAfpEntity = afpDAO.findAll();
        List<AfpDTO> listAfpDTO = new ArrayList<>();
        AfpDTO afpDTO;
        for (AfpEntity afpEntity : listAfpEntity) {
            afpDTO = new AfpDTO();
            afpDTO.setIdAfp(afpEntity.getId());
            afpDTO.setNombreAfp(afpEntity.getNombreAfp());
            listAfpDTO.add(afpDTO);
        }
        AfpDTO AfpDTO = listAfpDTO.stream().filter(afp -> afp.getIdAfp() == 1).findFirst().get();
        if (AfpDTO != null) {
            listAfpDTO.remove(AfpDTO);
            listAfpDTO.add(0, AfpDTO);
        }
        Collections.sort(listAfpDTO);
        return listAfpDTO;
    }

    public List<EpsDTO> getEpsDTOS() {
        List<EpsEntity> listEpsEntity = epsDAO.findAll();
        List<EpsDTO> listEpsDTO = new ArrayList<>();
        EpsDTO epsDTO;
        for (EpsEntity epsEntity : listEpsEntity) {
            epsDTO = new EpsDTO();
            epsDTO.setIdEps(epsEntity.getId());
            epsDTO.setNombreEps(epsEntity.getNombreEps());
            listEpsDTO.add(epsDTO);
        }
        Collections.sort(listEpsDTO);
        return listEpsDTO;
    }

    public List<ArlDTO> getArlDTOS() {
        List<ArlEntity> listArlEntity = arlDAO.findAll();
        List<ArlDTO> listArlDTO = new ArrayList<>();
        ArlDTO arlDTO;
        for (ArlEntity arlEntity : listArlEntity) {
            arlDTO = new ArlDTO();
            arlDTO.setIdArl(arlEntity.getId());
            arlDTO.setNombreArl(arlEntity.getNombreArl());
            listArlDTO.add(arlDTO);
        }
        Collections.sort(listArlDTO);
        return listArlDTO;
    }

    public List<OperadoresPilaDTO> getOperadoresPilaDTOS() {
        List<OperadoresPilaEntity> listOperadoresPilaEntity = operadoresPilaDAO.findAll();
        List<OperadoresPilaDTO> listOperadoresPilaDTO = new ArrayList<>();
        OperadoresPilaDTO operadoresPilaDTO;
        for (OperadoresPilaEntity operadoresPilaEntity : listOperadoresPilaEntity) {
            operadoresPilaDTO = new OperadoresPilaDTO();
            operadoresPilaDTO.setCodigoOperador(operadoresPilaEntity.getCodigo());
            operadoresPilaDTO.setIdOperador(operadoresPilaEntity.getId());
            operadoresPilaDTO.setNombre(operadoresPilaEntity.getNombre());
            operadoresPilaDTO.setOperador(operadoresPilaEntity.getOperador());
            listOperadoresPilaDTO.add(operadoresPilaDTO);
        }
        Collections.sort(listOperadoresPilaDTO);
        return listOperadoresPilaDTO;
    }

    public List<TipoDocumentoDTO> getTipoDocumentoDTOS() {
        List<TipoDocumentoEntity> listTipoDocumentoEntity = tipoDocumentoDAO.findAll();
        List<TipoDocumentoDTO> listTipoDocumentoDTO = new ArrayList<>();
        TipoDocumentoDTO tipoDocumentoDTO;
        for (TipoDocumentoEntity tipoDocumentoEntity : listTipoDocumentoEntity) {
            tipoDocumentoDTO = new TipoDocumentoDTO();
            tipoDocumentoDTO.setId(tipoDocumentoEntity.getId());
            tipoDocumentoDTO.setDescripcionTipoDocumento(tipoDocumentoEntity.getDescripcionTipoDoc());
            listTipoDocumentoDTO.add(tipoDocumentoDTO);
        }
        Collections.sort(listTipoDocumentoDTO);
        return listTipoDocumentoDTO;
    }

    public CreateDriverDTO mapDriverDTO(ConductorVehiculoDTO condVeh){
        CreateDriverDTO result = new CreateDriverDTO();
        result.setConductorDTO(condVeh.getConductorDTO());
        result.setArl(condVeh.getArl());
        result.setPlanillas(condVeh.getPlanillas());
        result.setIdEmpresa(condVeh.getEmpresaDTO().getId());
        result.setEmpresaDTO(condVeh.getEmpresaDTO());
        result.setFechaVencimientoSoat(condVeh.getFechaVencimientoSoat());
        result.setFechaVencimientoRtm(condVeh.getFechaVencimientoRtm());
        result.setFechaVencimientoTO(condVeh.getFechaVencimientoTO());
        result.setNroSOAT(condVeh.getNroSOAT());
        result.setNroRTM(condVeh.getNroRTM());
        result.setNroTarjetaOperacion(condVeh.getNroTarjetaOperacion());
        result.setFactorCalidad(condVeh.isFactorCalidad());
        ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
        byte[] data = null;
        try {

            ConductorEntity conductorEntity = null;
            if (condVeh.getId() != 0) {
                conductorEntity = conductorDAO.findByPrimaryKey(condVeh.getId());
            }
            if (conductorEntity != null) {
                String filename = conductorEntity.getRutaFoto();
                if (filename != null && !filename.isEmpty()) {
                    if (!filename.contains(".jpg")) {
                        filename += ".jpg";
                    }
                    if (!filename.contains(rutaImg.getValorParametro())) {
                        filename = rutaImg.getValorParametro().concat(filename);
                    }
                    Path path = Paths.get(filename);
                    data = Files.readAllBytes(path);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        result.getConductorDTO().setFoto(data!=null? Base64.getEncoder().encodeToString(data):"");
        result.setIdMetodoCobro(condVeh.getIdMetodoCobro());
        result.setIdTipoServicio(condVeh.getIdTipoServicio());
        result.setIdConductor(condVeh.getConductorDTO().getId());
        result.setNumeroTarjetaControl(condVeh.getNumeroTarjetaControl());
        result.setFechaVigencia(condVeh.getFechaVigencia());
        result.setPlaca(condVeh.getPlaca());
        result.setId(condVeh.getId());
        return result;
    }
}
