truncate table SMB_PARAMETRO_SIMUR; 

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (1, 'simur.param.alerta.dias.vencimiento', 'Numero de dias para dar la alerta anteriores a la fecha de vencimiento de la tarjeta de control', 1, 10);

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (2, 'simur.param.vigencia.tarjeta', 'Vigencia de la tarjeta en dias', 1, 30);

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (3, 'simur.param.validez.tarjeta', 'Validez de la tarjeta en dias', 1, 365);

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (4, 'simur.param.ldap.url', 'LDAP_URL', 1, 'ldap://192.168.100.120:389/');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (5, 'simur.param.ldap.usuario', 'LDAP_USUARIO', 1, 'Prueba Fabrica de Software');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (6, 'simur.param.ldap.prefijo', 'LDAP_PREFIJO', 1, 'SDM\\');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (7, 'simur.param.ldap.clave', 'LDAP_CLAVE', 1, 'movilidad2017');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (8, 'simur.param.ldap.seguridad', 'LDAP_SECURITY_AUTHENTICATION', 1, 'simple');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (9, 'simur.param.ldap.base', 'LDAP_BASE', 1, 'DC=movilidadbogota,DC=gov,DC=co');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (10, 'simur.param.ldap.filtro', 'LDAP_FILTER', 1, '&(objectClass=user)(sAMAccountName=');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (11, 'simur.param.uri.img', 'Ruta donde se almaceran las fotos de los conductores', 1, '/home/zenware/');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (12, 'core.administrator.email.to', 'Correo del usuario administrador', 1, 'frogelez@zenware.com.co');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (13, 'core.notification.email.from', 'Este parámetro establece la dirección de correo electrónico utilizada para enviar correos electrónicos.', 1, 'develop.zenware@gmail.com');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (14, 'core.notification.email.password', 'Este parámetro es la contraseña de la dirección de correo electrónico.', 1, 'Develop.Zenware123!');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (15, 'core.notification.email.smtp.host', 'Este parámetro establece el host para el protocolo SMTP.', 1, 'smtp.gmail.com');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (16, 'core.notification.email.smtp.port', 'Este parámetro establece el puerto para el protocolo SMTP.', 1, '587');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (17, 'core.notification.email.socketFactory.class', 'Este parámetro establece el puerto para el protocolo SMTP.', 1, 'javax.net.ssl.SSLSocketFactory');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (18, 'core.notification.email.socketFactory.port', 'Este parámetro establece el puerto para SMTP socketFactory.', 1, '587');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (19, 'core.notification.email.auth', 'Este parámetro establece si se debe generar autentificacion para el correo de salida.', 1, 'true');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (20, 'simur.administration.contextUrl', 'Este parámetro establece la URL de contexto para el sistema de información.' , 1, 'http://localhost:8080/sirc');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (21, 'simur.municipio.solicitud.duups', 'Este parámetro establece el municipio a enviar para duups.' , 1, '11001000');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO,  DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO)
VALUES (22, 'simur.departamento.solicitud.duups', 'Este parámetro establece el departamento a enviar para duups.' , 1, '11');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (23, 'ENDPOINT_DUUPS', 'Endpoint Duups', 1, 'http://192.168.100.67:8080/PersonasDUUPS/ServicioReceptorPersonaDUUPS?wsdl');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (24, 'CABECERA_IPCLIENTE_DUUPS', 'Cabecera ip Cliente Duups', 1, '192.168.100.67');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (25, 'CABECERA_PASSWORD_DUUPS', 'Cabecera password Duups', 1, 'ws2017.');

INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
VALUES (26, 'CABECERA_USUARIO_DUUPS', 'Cabecera usuario Duups', 1, 'wsduups');

drop sequence sq_parametro;

create sequence sq_parametro start with 27 nocache;

commit;

/*--LDAP SDM
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.url', 'LDAP_URL', 1, 'ldap://192.168.100.120:636');
--
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.usuario', 'LDAP_USUARIO', 1, 'pruebafabricasof');
--
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.clave', 'LDAP_CLAVE', 1, 'movilidad2017');
--
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.prefijo', 'LDAP_PREFIJO', 1, '');
--
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.seguridad', 'LDAP_SECURITY_AUTHENTICATION', 1, 'simple');
--
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.base', 'LDAP_BASE', 1, 'DC=movilidadbogota,DC=gov,DC=co');
--
--INSERT INTO SMB_PARAMETRO_SIMUR (ID_PARAMETRO, CODIGO_PARAMETRO, DESCRIPCION_PARAMETRO, ID_ESTADO, VALOR_PARAMETRO) 
--VALUES (SQ_PARAMETRO.NEXTVAL, 'simur.param.ldap.filtro', 'LDAP_FILTER', 1, '&(objectClass=user)(sAMAccountName=');*/

