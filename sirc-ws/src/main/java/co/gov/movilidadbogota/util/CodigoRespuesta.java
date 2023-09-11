package co.gov.movilidadbogota.util;

public enum CodigoRespuesta {

    EXITO("0", "EXITOSO"),
    ERROR_CONEXION_BASE_DE_DATOS("1", "No hubo conexión con la base de datos."),
    ERROR_CONEXION_SERVICIO_DUUPS("2", "No hubo conexión con el Servicio DUUPS + %s "),
    ERROR_INESPERADO("3", "Error inesperado + %s "),
    ERROR_PARAMETROS_OBLIGATORIOS("4", "El parámetro %s es Obligatorio. Por favor revise los parámetros obligatorios para la operación requerida."),
    ERROR_PARAMETROS_FORMATO("5", "El valor del parámetro %s  no tiene el formato o tipo de dato correcto. Por favor revise los parámetros de la operación requerida."),
    ERROR_IDENTIFICACION_CONDUCTOR("6", "El número de identificación del conductor no es válido."),
    ERROR_CODIGO_EMPRESA_NO_COINCIDE("7", "El código de %s  no coincide con los parametrizados."),
    ERROR_CAMBIO_DATOS_TARJETA_CONTROL("8", "Existe un cambio de datos para la tarjeta de control %s suministrada. "
            + "Por favor, revise si requiere expedir una nueva tarjeta con los datos suministrados o efectuar la corrección de los datos para volver a intentar el proceso de Refrendación. "),
    ERROR_NUMERO_APROBACION_PAGO_PILA("9", "El número de aprobación del pago PILA %s ya se encuentra asociada a una Tarjeta de Control."),
    ERROR_CONDUCTOR_NO_EXISTE("10", "El número de identificación del conductor no se encuentra registrado para la operación %s . "),
    ERROR_EXPEDICION_DATOS_TARJETA_CONTROL("11", "El parámetro tarjeta control con numero %s no es requerido para el proceso de Expedición. "),
    ERROR_NO_EXISTE_DATOS_TARJETA_CONTROL("12", "El parámetro número tarjeta control con número %s no está registrado en el sistema. "),
    ERROR_COMBINACION_CONDUCTOR_VEHICULO_EXISTENTE("13", "La combinación de conductor con identificación %s "
            + "y el vehículo de placas %s ya está registrada en el sistema y no es válida para el tipo de transacción Expedición. Si desea expedir una nueva tarjeta de control, debe cancelar esta tarjeta"),
    ERROR_AUTENTICACION_USUARIO("14", "Error de autenticación. Usuario y/o clave invalida. "),
    ERROR_AUTENTICACION_USUARIO_ESTADO("15", "Error de autenticación. Usuario inactivo. "),
    ERROR_AUTENTICACION_TIPO_USUARIO("16", "Error de autenticación. Usuario sin permiso para ejecutar el proceso. "),
    ERROR_METODO_COBRO("17", "El método de cobro no se encuentra vigente. "),
    ERROR_TIPO_SERVICIO("18", "El tipo de servicio no se encuentra vigente. "),
    INCONSISTENCIA_REFRENDACION_TARJTEA_CONTROL("19", "La fecha de validez de la tarjeta de control excede la fecha actual. Se debe expedir una nueva tarjeta de control. "),
    ERROR_PERIODO_PAGO_SEGURIDAD("20", "Periodo de pago invalido. "),
    ERROR_VIGENCIA_AFTER_DATE("21","Esta tarjeta ya se encuentra refrendada."),
    ERROR_VIGENCIA_AFTER_DATE_DETAIL("22","Esta tarjeta ya se encuentra refrendada, con fecha de vencimiento "),
    ERROR_PLACA_PATTERN("23","El numero de placa no tiene un patron Valido."),
    SIM_ERROR_VEHICULO_NOEXISTE("30", "El vehículo no se encuentra registrado en el SIM. "),
    SIM_ERROR_ESTADO_VEHICULO("31", "El vehículo no se encuentra activo. "),
    SIM_ERROR_SERVICIO_VEHICULO("32", "El vehículo no pertenece al servicio de transporte público. "),
    SIM_ERROR_EMPRESA_VEHICULO("33", "El vehículo no se encuentra vinculado con la empresa que actúa como emisora de la tarjeta de control. "),
    SIM_ERROR_NRO_TARJETA_OPERACION_VEHICULO("34", "El número de la tarjeta de operación no corresponde al registrado para el vehículo. "),
    SIM_ERROR_FECHA_VEN_TARJETA_OPERACION_VEHICULO("35", "La fecha de vencimiento de la tarjeta de operación no corresponde a la registrada para el vehículo. "),
    SIM_ERROR_TARJETA_OPERACION_INACTIVA("36", "La tarjeta de operación no se encuentra activa o vigente. "),
    ERROR_PLANILLAS("37", "Ha superado el número máximo de planillas: %s . "),
    ERROR_REFRENDACION_ESTADO_5("38","No es posible realizar la refrendaci\u00f3n, la tarjeta de control %s estuvo vigente hasta %s, debe expedir una nueva tarjeta de control. "),
    ERROR_REFRENDACION_ESTADO_3("39","No es posible realizar la refrendaci\u00f3n, la tarjeta de control %s fue cancelada por la empresa, debe expedir una nueva tarjeta de control. ");

    String codigoRespuesta;

    String descripcionRespuesta;

    CodigoRespuesta(String codigoRespuesta, String descripcionRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

}
