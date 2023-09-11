package co.gov.movilidadbogota.core.util;

public enum SystemParameters {

	VIGENCIA_TARJETA("simur.param.vigencia.tarjeta"), VALIDEZ_TARJETA("simur.param.validez.tarjeta"),
	VENCIMIENTO_TARJETA("simur.param.alerta.dias.vencimiento"),URI_IMG("simur.param.uri.img"),
	EMAIL_TO("core.administrator.email.to"),EMAIL_FROM("core.notification.email.from"),
	EMAIL_PASSWORD("core.notification.email.password"),EMAIL_HOST("core.notification.email.smtp.host"),
	EMAIL_PORT("core.notification.email.smtp.port"),EMAIL_AUTH("core.notification.email.auth"),
	EMAIL_SOCKETFACTORY_PORT("core.notification.email.socketFactory.port"),
	EMAIL_SOCKETFACTORY_CLASS("core.notification.email.socketFactory.class"), 
	ADMINISTRATION_CONTEXTURL("simur.administration.contextUrl"), ENDPOINT_DUUPS("ENDPOINT_DUUPS"), 
	CABECERA_IPCLIENTE_DUUPS("CABECERA_IPCLIENTE_DUUPS"), CABECERA_PASSWORD_DUUPS("CABECERA_PASSWORD_DUUPS"), 
	CABECERA_USUARIO_DUUPS("CABECERA_USUARIO_DUUPS"),
	MUNICIPIO_DUUPS("simur.municipio.solicitud.duups"),
	DEPARTAMENTO_DUUPS("simur.departamento.solicitud.duups"),
	NOT_TARJEAS_SIN_VIGENCIA("NOT_TARJEAS_SIN_VIGENCIA"),
	NOT_TARJEAS_SIN_VALIDEZ("NOT_TARJEAS_SIN_VALIDEZ"),
	NOT_TARJEAS_PROX_VENCERSE("NOT_TARJEAS_PROX_VENCERSE"),
	NOT_EMAIL_TIMER("NOT_EMAIL_TIMER"),
	CODIGO_ORIGEN_SIRC_DUUPS("CODIGO_ORIGEN_SIRC_DUUPS"),
        FECHA_FIN_METODO_COBRO_UNIDADES("FECHA_FIN_METODO_COBRO_UNIDADES"),
        FECHA_INICIO_NIVEL_SERVICIO_LUJO("FECHA_INICIO_NIVEL_SERVICIO_LUJO"),
        DISPOSITIVO_MOVIL_REQUERIDO_FACTOR_CALIDAD("DISPOSITIVO_MOVIL_REQUERIDO_FACTOR_CALIDAD"),
        UTILIZAR_SERVICIO_WEB_REGISTRADURIA("UTILIZAR_SERVICIO_WEB_REGISTRADURIA"),
        CANTIDAD_MAX_PLANILLAS("CANTIDAD_MAX_PLANILLAS"),URL_SIMUR_QR("simur.param.url.qr")
	,URL_APITAXIS_METODO_COBRO("simur.param.url.apitaxi");

	private String value;

	private SystemParameters(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	};

}
