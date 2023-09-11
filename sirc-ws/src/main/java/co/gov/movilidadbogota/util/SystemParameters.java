/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.util;

/**
 *
 * @author USER-LENOVO
 */
public enum SystemParameters {

    VIGENCIA_TARJETA("simur.param.vigencia.tarjeta"), VALIDEZ_TARJETA("simur.param.validez.tarjeta"),
    VENCIMIENTO_TARJETA("simur.param.alerta.dias.vencimiento"), URI_IMG("simur.param.uri.img"),URI_QR_IMG("simur.param.uri.qr.img"),
    ENDPOINT_DUUPS("ENDPOINT_DUUPS"), MUNICIPIO_DUUPS("simur.municipio.solicitud.duups"), DEPARTAMENTO_DUUPS("simur.departamento.solicitud.duups"),
    CABECERA_IPCLIENTE_DUUPS("CABECERA_IPCLIENTE_DUUPS"), CABECERA_PASSWORD_DUUPS("CABECERA_PASSWORD_DUUPS"),
    CABECERA_USUARIO_DUUPS("CABECERA_USUARIO_DUUPS"), CODIGO_ORIGEN_SIRC_DUUPS("CODIGO_ORIGEN_SIRC_DUUPS"),
    FECHA_FIN_METODO_COBRO_UNIDADES("FECHA_FIN_METODO_COBRO_UNIDADES"),
    FECHA_INICIO_NIVEL_SERVICIO_LUJO("FECHA_INICIO_NIVEL_SERVICIO_LUJO"),
    DISPOSITIVO_MOVIL_REQUERIDO_FACTOR_CALIDAD("DISPOSITIVO_MOVIL_REQUERIDO_FACTOR_CALIDAD"),
    UTILIZAR_SERVICIO_WEB_REGISTRADURIA("UTILIZAR_SERVICIO_WEB_REGISTRADURIA"),
    CANTIDAD_MAX_PLANILLAS("CANTIDAD_MAX_PLANILLAS"),URL_SIMUR_QR("simur.param.url.qr"),URL_APITAXIS_METODO_COBRO("simur.param.url.apitaxi");

    private String value;

    private SystemParameters(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
