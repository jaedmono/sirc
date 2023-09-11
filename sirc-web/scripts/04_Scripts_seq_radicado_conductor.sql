ALTER TABLE SMB_PARAMETRO_SIMUR MODIFY VALOR_PARAMETRO VARCHAR2(1000);

INSERT INTO SMB_PARAMETRO_SIMUR
(ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, FECHA_MODIFICA, USR_MODIFICA, VALOR_PARAMETRO)
VALUES(27, 'NOT_TARJEAS_SIN_VIGENCIA', 'Plantilla para el envio de correo de las tarjetas sin vigencia', 1, SYSDATE, 'admin', '<p><h1>TARJETAS DE CONTROL SIN VIGENCIA</h1><br/></p>
En este momento las siguientes tarjetas se encuentran sin vigencia:
<table border="1">
<tr><th><b>Núméro de Tarjeta de Control</b></th><tr>
${tarjetas}
</table>
<br>
<p>Para consultar el detalle de las tarjetas relacionadas anteriormente, por favor ingrese al aplicativo </p>
<p>Atentamente.</p>
<p><h1>Secretaría Distrital de Movilidad</h1></p>');


INSERT INTO SMB_PARAMETRO_SIMUR
(ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, FECHA_MODIFICA, USR_MODIFICA, VALOR_PARAMETRO)
VALUES(28, 'NOT_TARJEAS_SIN_VALIDEZ', 'Plantilla para el envio de correo de las tarjetas sin validez', 1, SYSDATE, 'admin', '<p><h1>TARJETAS DE CONTROL SIN VALIDEZ</h1><br/></p>
<p>En este momento las siguientes tarjetas se encuentran sin validez:</p>
<table border="1">
<tr><th><b>Núméro de Tarjeta de Control</b></th><tr>
${tarjetas}
</table>
<br>
<p>Para consultar el detalle de las tarjetas relacionadas anteriormente, por favor ingrese al aplicativo </p>
<p>Atentamente.</p>
<p><h1>Secretaría Distrital de Movilidad</h1></p>');


INSERT INTO SMB_PARAMETRO_SIMUR
(ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, FECHA_MODIFICA, USR_MODIFICA, VALOR_PARAMETRO)
VALUES(29, 'NOT_TARJEAS_PROX_VENCERSE', 'Plantilla para el envio de correo de las tarjetas proximas a vencerse', 1, SYSDATE, 'admin', '<p><h1>TARJETAS DE CONTROL PRÓXIMAS A VENCERSE</h1><br/></p>
<p>En este momento las siguientes tarjetas se encuentran próximas a vencerse</p>
<table border="1">
<tr><th><b>Núméro de Tarjeta de Control</b></th><tr>
${tarjetas}
</table>
<br>
<p>Para consultar el detalle de las tarjetas relacionadas anteriormente, por favor ingrese al aplicativo </p>
<p>Atentamente.</p>
<p><h1>Secretaría Distrital de Movilidad</h1></p>');


INSERT INTO SMB_PARAMETRO_SIMUR
(ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, FECHA_MODIFICA, USR_MODIFICA, VALOR_PARAMETRO)
VALUES(30, 'NOT_EMAIL_TIMER', 'Intervalo de tiempo para el envio de notificaciones, en dias', 1, SYSDATE, 'admin', '1');

ALTER TABLE SMB_CONDUCTOR_VEHICULO ADD NOTIFADO NUMBER(1,0) DEFAULT 0 NOT NULL ;

COMMIT;