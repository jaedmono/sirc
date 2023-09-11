/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.web.controller;

/**
 *
 * @author yalvarez
 */
import co.gov.movilidadbogota.core.dao.ArlDAO;
import co.gov.movilidadbogota.core.dao.ConductorVehiculoDAO;
import co.gov.movilidadbogota.core.dao.EmpresaDAO;
import co.gov.movilidadbogota.core.dao.EpsDAO;
import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import co.gov.movilidadbogota.core.dto.ConductorDTO;
import co.gov.movilidadbogota.core.dto.ConductorVehiculoDTO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.dto.PersonaDTO;
import co.gov.movilidadbogota.core.entity.ArlEntity;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;
import co.gov.movilidadbogota.core.entity.EpsEntity;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.web.constants.CompanyConstants;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class ConductorVehiculoController {

    @Autowired
    private ConductorVehiculoDAO conductorVehiculoDAO;

    @Autowired
    private EpsDAO epsDAO;

    @Autowired
    private ArlDAO arlDAO;

    @Autowired
    private EmpresaDAO empresaDAO;

    @Autowired
    private ParametroSimurDAO parametroSimurDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConductorVehiculoController.class);

    public List<ConductorVehiculoEntity> getConductores() {
        List<ConductorVehiculoEntity> conductores = conductorVehiculoDAO.findAll();
        return conductores;
    }

    public List<EpsEntity> getEps() {
        List<EpsEntity> eps = epsDAO.findAll();
        return eps;
    }

    public List<ArlEntity> getArl() {
        List<ArlEntity> arl = arlDAO.findAll();
        return arl;
    }

    public List<EmpresaEntity> getEmpresas() {
        List<EmpresaEntity> arl = empresaDAO.findAll();
        return arl;
    }

    @RequestMapping(value = CompanyConstants.CONSULT_CONDUCTORES_PLACA, method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    ResponseEntity<List> consultarTaxiPorPlaca(@RequestParam String text) {
        LOGGER.info("########## consultarTaxiPorPlaca ##########");
        List<ConductorVehiculoDTO> resultado = new ArrayList<>();
        Boolean existe = false;
        List<ConductorVehiculoEntity> result = conductorVehiculoDAO.consultarTaxiPorPlaca(text);
        if (result != null && !result.isEmpty()) {
            for (ConductorVehiculoEntity conductores : result) {
                if (conductores.getPlacaSerialVehiculo().contains(text) && conductores.getEmpresa().getIdEstado() == 1) {
                    existe = true;
                    ConductorVehiculoDTO conductor1 = new ConductorVehiculoDTO();
                    ConductorDTO conductor = new ConductorDTO();
                    PersonaDTO persona = new PersonaDTO();
                    persona.setApellidos(conductores.getConductor().getPersona().getApellidos());
                    persona.setNombres(conductores.getConductor().getPersona().getNombres());
                    persona.setNumeroIdentificacion(conductores.getConductor().getPersona().getNumeroDocumento());
                    conductor.setId(conductores.getConductor().getId());
                    conductor1.setFechaExpedicion(conductores.getFechaExpedicion());
                    conductor1.setFechaValidez(conductores.getFechaValidez());
                    conductor1.setFechaVencimientoRtm(conductores.getFechaVencimientoRtm());
                    conductor1.setFechaVencimientoSoat(conductores.getFechaVencimientoSoat());
                    conductor1.setFechaVencimientoTO(conductores.getFechaVencimientoTO());
                    conductor1.setFechaVigencia(conductores.getFechaVigencia());
                    conductor1.setNroRTM(conductores.getNroRTM());
                    conductor1.setNroSOAT(conductores.getNroSOAT());
                    conductor1.setNroTarjetaOperacion(conductores.getNroTarjetaOperacion());
                    conductor1.setNumeroTarjetaControl(conductores.getTarjetaControl());
                    conductor1.setPlaca(conductores.getPlacaSerialVehiculo());
                    String rh = conductores.getConductor().getGrupoSanguineo() + "" + conductores.getConductor().getFactorRh();
                    conductor1.setTipoSangre(rh);
                    conductor1.setId(conductores.getId());
                    conductor1.setIdMetodoCobro(conductores.getMetodoPago().getId());
                    conductor1.setNombreMetodoCobro(conductores.getMetodoPago().getDescripcion());
                    for (EpsEntity eps : getEps()) {
                        if (eps.getId() == conductores.getConductor().getIdEps()) {
                            conductor1.setEps(eps.getNombreEps());
                        }
                    }
                    for (ArlEntity arl : getArl()) {
                        if (arl.getId() == conductores.getConductor().getIdArl()) {
                            conductor1.setArl(arl.getNombreArl());
                        }
                    }
                    for (EmpresaEntity empresa : getEmpresas()) {
                        if (empresa.getId() == conductores.getEmpresa().getId()) {
                            conductor1.setNombreEmpresa(empresa.getNombreRazonSocial());
                            conductor1.setNitEmpresa(empresa.getIdNitEmpresa());
                        }
                    }
                    byte[] img = getImg(conductores.getConductor().getRutaFoto());
                    conductor1.setFoto(img!=null?Base64.getEncoder().encodeToString(img):"");
                    conductor.setPersona(persona);
                    conductor1.setConductorDTO(conductor);
                    resultado.add(conductor1);
                }
            }
        }
        if (!existe) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
    }

    @RequestMapping(value = CompanyConstants.CONSULT_CONDUCTORES_TARJETA, method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    ResponseEntity<ConductorVehiculoDTO> consultarConductorPortarjeta(@RequestParam String text) {
        LOGGER.info("########## consultarConductorPortarjeta ##########");
        ConductorVehiculoDTO resultado = new ConductorVehiculoDTO();
        Boolean existe = false;
        ConductorVehiculoEntity conductores = conductorVehiculoDAO.consultarConductorPortarjeta(text);
        if (conductores != null) {
            existe = true;
            ConductorVehiculoDTO conductor1 = new ConductorVehiculoDTO();
            ConductorDTO conductor = new ConductorDTO();
            PersonaDTO persona = new PersonaDTO();
            persona.setApellidos(conductores.getConductor().getPersona().getApellidos());
            persona.setNombres(conductores.getConductor().getPersona().getNombres());
            persona.setNumeroIdentificacion(conductores.getConductor().getPersona().getNumeroDocumento());
            conductor.setId(conductores.getConductor().getId());
            conductor1.setFechaExpedicion(conductores.getFechaExpedicion());
            conductor1.setFechaValidez(conductores.getFechaValidez());
            conductor1.setFechaVencimientoRtm(conductores.getFechaVencimientoRtm());
            conductor1.setFechaVencimientoSoat(conductores.getFechaVencimientoSoat());
            conductor1.setFechaVencimientoTO(conductores.getFechaVencimientoTO());
            conductor1.setFechaVigencia(conductores.getFechaVigencia());
            conductor1.setNroRTM(conductores.getNroRTM());
            conductor1.setNroSOAT(conductores.getNroSOAT());
            conductor1.setNroTarjetaOperacion(conductores.getNroTarjetaOperacion());
            conductor1.setNumeroTarjetaControl(conductores.getTarjetaControl());
            conductor1.setPlaca(conductores.getPlacaSerialVehiculo());
            conductor1.setId(conductores.getId());
            conductor1.setNombreMetodoCobro(conductores.getMetodoPago().getDescripcion());
            conductor1.setIdMetodoCobro(conductores.getMetodoPago().getId());
            String rh = conductores.getConductor().getGrupoSanguineo() + "" + conductores.getConductor().getFactorRh();
            conductor1.setTipoSangre(rh);
            for (EpsEntity eps : getEps()) {
                if (eps.getId() == conductores.getConductor().getIdEps()) {
                    conductor1.setEps(eps.getNombreEps());
                }
            }
            for (ArlEntity arl : getArl()) {
                if (arl.getId() == conductores.getConductor().getIdArl()) {
                    conductor1.setArl(arl.getNombreArl());
                }
            }
            for (EmpresaEntity empresa : getEmpresas()) {
                if (empresa.getId() == conductores.getEmpresa().getId()) {
                    conductor1.setNombreEmpresa(empresa.getNombreRazonSocial().trim());
                    conductor1.setNitEmpresa(empresa.getIdNitEmpresa());
                }
            }
            byte[] img = getImg(conductores.getConductor().getRutaFoto());
            conductor1.setFoto(img!=null?Base64.getEncoder().encodeToString(img):"");
            conductor.setPersona(persona);
            conductor1.setConductorDTO(conductor);
            resultado = conductor1;
        }
        if (!existe) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
    }

    public byte[] getImg(String ruta) {
        try {
            if (ruta != null && !ruta.isEmpty()) {
                ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
                byte[] data = null;
                if (!ruta.contains(".jpg")) {
                    ruta += ".jpg";
                }
                if (!ruta.contains(rutaImg.getValorParametro())) {
                    ruta = rutaImg.getValorParametro().concat(ruta);
                }
                LOGGER.info("##### URI_IMG: " + ruta);
                Path path = Paths.get(ruta);
                data = Files.readAllBytes(path);
                return data;
            }
        } catch (Exception e) {
            LOGGER.error("##### getImg #####", e);
        }
        return null;
    }
}
