
/*Eliminacion de llaves Foraneas*/;

  
    drop table SMB_AFP cascade constraints;

    drop table SMB_APLICACION cascade constraints;

    drop table SMB_ARL cascade constraints;

    drop table SMB_AUDITORIA_SIRC cascade constraints;

    drop table SMB_CONDUCTOR_VEHICULO cascade constraints;

    drop table SMB_EPS cascade constraints;

    drop table SMB_FUNCIONARIO_SM cascade constraints;

    drop table SMB_OPCIONMENU cascade constraints;

    drop table SMB_PARAMETRO_SIMUR cascade constraints;

    drop table SMB_PERFIL cascade constraints;

    drop table SMB_PERFILXOPCION_MENU cascade constraints;

    drop table SMB_TARJETA_CONTROL_ESTADO cascade constraints;

    drop table SMB_TIPO_APLICACION cascade constraints;

    drop table SMB_TIPO_ASIGNA cascade constraints;

    drop table SMB_TIPO_DOCUMENTO cascade constraints;

    drop table SMB_TIPO_USUARIO cascade constraints;

    drop table SMI_CONDUCTOR cascade constraints;

    drop table SMI_CONTROLALERTA cascade constraints;

    drop table SMI_EMPRESA cascade constraints;

    drop table SMI_EMPRESA_USUARIO cascade constraints;

    drop table SMI_PAGOPILA_COND_VEHI cascade constraints;

    drop table SMI_PERSONA cascade constraints;

    drop table SMI_USUARIO cascade constraints;

    drop sequence sq_afp;

    drop sequence sq_aplicacion;

    drop sequence sq_arl;

    drop sequence sq_auditoria;

    drop sequence sq_conductor;

    drop sequence sq_ctrl_alerta;

    drop sequence sq_empresa;

    drop sequence sq_eps;

    drop sequence sq_funcionario;

    drop sequence sq_opcion_menu;

    drop sequence sq_pago_pila;

    drop sequence sq_parametro;

    drop sequence sq_perfil;

    drop sequence sq_perfil_menu;

    drop sequence sq_persona;

    drop sequence sq_tarjeta_control;

    drop sequence sq_tipo_aplicacion;

    drop sequence sq_tipo_asigna;

    drop sequence sq_tipo_documento;

    drop sequence sq_tipo_usuario;

    drop sequence sq_usuario;

    drop sequence sq_vehiculo;
    
    drop sequence SEQ_RADICADO_CODUCTOR;

create sequence sq_afp start with 1 increment by 1 nocache;
create sequence sq_aplicacion start with 1 increment by 1 nocache;
create sequence sq_arl start with 1 increment by 1 nocache;
create sequence sq_auditoria start with 1 increment by 1 nocache;
create sequence sq_conductor start with 1 increment by 1 nocache;
create sequence sq_ctrl_alerta start with 1 increment by 1 nocache;
create sequence sq_empresa start with 1 increment by 1 nocache;
create sequence sq_eps start with 1 increment by 1 nocache;
create sequence sq_funcionario start with 1 increment by 1 nocache;
create sequence sq_opcion_menu start with 1 increment by 1 nocache;
create sequence sq_pago_pila start with 1 increment by 1 nocache;
create sequence sq_parametro start with 1 increment by 1 nocache;
create sequence sq_perfil start with 1 increment by 1 nocache;
create sequence sq_perfil_menu start with 1 increment by 1 nocache;
create sequence sq_persona start with 2 increment by 1 nocache;
create sequence sq_tarjeta_control start with 1 increment by 1 nocache;
create sequence sq_tipo_aplicacion start with 1 increment by 1 nocache;
create sequence sq_tipo_asigna start with 1 increment by 1 nocache;
create sequence sq_tipo_documento start with 1 increment by 1 nocache;
create sequence sq_tipo_usuario start with 1 increment by 1 nocache;
create sequence sq_usuario start with 2 increment by 1 nocache;
create sequence sq_vehiculo start with 1 increment by 1 nocache;
CREATE sequence SEQ_RADICADO_CODUCTOR INCREMENT BY 1 MINVALUE 1000000 MAXVALUE 99999999 NOCYCLE CACHE 20 NOORDER;


/*Creacion de Tablas*/

     create table SMB_AFP (
        ID_AFP number(19,0) not null,
        CODIGO_AFP varchar2(255 char),
        DIRECCION_AFP varchar2(255 char),
        NIT_AFP varchar2(255 char),
        NOMBRE_AFP varchar2(255 char),
        TELEFONO_AFP varchar2(255 char),
        primary key (ID_AFP)
    );

    create table SMB_APLICACION (
        ID_APLICACION number(19,0) not null,
        ABREVIATURA_APLICACION varchar2(255 char),
        ID_ESTADO number(19,0),
        NOMBRE_APLICACION varchar2(255 char),
        ID_TIPO_APLICACION number(19,0),
        primary key (ID_APLICACION)
    );

    create table SMB_ARL (
        ID_ARL number(19,0) not null,
        CODIGO_ARL varchar2(255 char),
        DIRECCION_ARL varchar2(255 char),
        NIT_ARL varchar2(255 char),
        NOMBRE_ARL varchar2(255 char),
        TELEFONO_ARL varchar2(255 char),
        primary key (ID_ARL)
    );

    create table SMB_AUDITORIA_SIRC (
        ID_AUDITORIA_SIRC number(19,0) not null,
        FECHA_MODIFICA timestamp,
        OBSERVACION varchar2(255 char),
        TIPO_TRANSACCION varchar2(255 char),
        USR_MODIFICA varchar2(255 char),
        ID_CONDUCTOR number(19,0),
        ID_EMPRESA number(19,0),
        primary key (ID_AUDITORIA_SIRC)
    );

    create table SMB_CONDUCTOR_VEHICULO (
        ID_VEHICULO number(19,0) not null,
        FECHA_EXPEDICION timestamp,
        FECHA_VALIDEZ timestamp,
        FECHA_VIGENCIA timestamp,
        PLACA_SERIAL_VEHICULO varchar2(255 char),
        TARJETA_CONTROL varchar2(255 char),
        TIPO_TRANSACCION varchar2(255 char),
        ID_CONDUCTOR number(19,0),
        ID_EMPRESA number(19,0),
        ID_ESTADO number(19,0),
        primary key (ID_VEHICULO)
    );

    create table SMB_EPS (
        ID_EPS number(19,0) not null,
        CODIGO_EPS varchar2(255 char),
        DIRECCION_EPS varchar2(255 char),
        NIT_EPS varchar2(255 char),
        NOMBRE_EPS varchar2(255 char),
        TELEFONO_EPS varchar2(255 char),
        primary key (ID_EPS)
    );

    create table SMB_FUNCIONARIO_SM (
        ID_FUNCIONARIO number(19,0) not null,
        FECHA_MODIFICA timestamp,
        ID_DEPENDENCIA number(19,0),
        ID_ESTADO number(19,0),
        NOMBRE_DEPENDENCIA varchar2(255 char),
        USR_MODIFICA varchar2(255 char),
        ID_PERSONA number(19,0),
        ID_USUARIO number(19,0),
        primary key (ID_FUNCIONARIO)
    );

    create table SMB_OPCIONMENU (
        ID_OPCION_MENU number(19,0) not null,
        DESCRIPCION_OPCION varchar2(255 char),
        ENLACE_OPCION_MENU varchar2(255 char),
        FECHA_MODIFICA timestamp,
        ID_ESTADO number(19,0),
        TIPO_OPCION_MENU number(19,0),
        USUARIO_MODIFICA varchar2(255 char),
        ID_APLICACION number(19,0),
        primary key (ID_OPCION_MENU)
    );

    create table SMB_PARAMETRO_SIMUR (
        ID_PARAMETRO number(19,0) not null,
        CODIGO_PARAMETRO varchar2(255 char),
        DESCRIPCION_PARAMETRO varchar2(255 char),
        ID_ESTADO number(1,0),
        FECHA_MODIFICA timestamp,
        USR_MODIFICA varchar2(255 char),
        VALOR_PARAMETRO varchar2(255 char),
        ID_APLICACION number(19,0),
        primary key (ID_PARAMETRO)
    );

    create table SMB_PERFIL (
        ID_PERFIL number(19,0) not null,
        DESCRIPCION_PERFIL varchar2(255 char),
        FECHA_MODIFICA timestamp,
        ID_ESTADO number(1,0),
        USUARIO_MODIFICA varchar2(255 char),
        primary key (ID_PERFIL)
    );

    create table SMB_PERFILXOPCION_MENU (
        ID_PERFILXOPCION_MENU number(19,0) not null,
        FECHA_FINAL_PERMISO timestamp,
        FECHA_INICIA_PERMISO timestamp,
        FECHA_MODIFICA timestamp,
        ID_ESTADO number(19,0),
        USR_MODIFICA varchar2(255 char),
        ID_OPCION_MENU number(19,0),
        ID_PERFIL number(19,0),
        primary key (ID_PERFILXOPCION_MENU)
    );

    create table SMB_TARJETA_CONTROL_ESTADO (
        ID number(19,0) not null,
        activo number(1,0),
        descripcion varchar2(255 char),
        primary key (ID)
    );

    create table SMB_TIPO_APLICACION (
        ID_TIPO_APLICACION number(19,0) not null,
        DESCRIPCION_TIPOAPLICACION varchar2(255 char),
        primary key (ID_TIPO_APLICACION)
    );

    create table SMB_TIPO_ASIGNA (
        ID_TIPO_ASIGNA number(19,0) not null,
        DESCRIPCION_ASIGNA varchar2(255 char),
        primary key (ID_TIPO_ASIGNA)
    );

    create table SMB_TIPO_DOCUMENTO (
        ID_TIPO_DOCUMENTO number(19,0) not null,
        DESCRIPCION_TIPODOC varchar2(255 char),
        HOMOLOGA_DUUPS varchar2(2 char),
        primary key (ID_TIPO_DOCUMENTO)
    );

    create table SMB_TIPO_USUARIO (
        ID_TIPO_USUARIO number(19,0) not null,
        DESCRIPCION_TIPO varchar2(255 char),
        primary key (ID_TIPO_USUARIO)
    );

    create table SMI_CONDUCTOR (
        ID_CONDUCTOR number(19,0) not null,
        FACTOR_RH varchar2(255 char),
        FECHA_MODIFICA timestamp,
        GRUPO_SANGUINEO varchar2(255 char),
        ID_AFP number(19,0),
        ID_ARL number(19,0),
        ID_EPS number(19,0),
        RUTA_FOTO varchar2(255 char),
        USUARIO_MODIFICA varchar2(255 char),
        ID_PERSONA number(19,0),
        primary key (ID_CONDUCTOR)
    );

    create table SMI_CONTROLALERTA (
        ID_CTRL_ALERTA number(19,0) not null,
        CORREO_ELECTRONICO varchar2(255 char),
        FECHA_NOTIFICACION timestamp,
        FECHA_REVISION_GA timestamp,
        ID_ESTADO number(19,0),
        INDICA_NOTIF_PROXIMO varchar2(255 char),
        INDICA_NOTIF_PROXIMO_GA varchar2(255 char),
        INDICA_NOTIF_SINVALIDEZ varchar2(255 char),
        INDICA_NOTIF_SINVALIDEZ_GA varchar2(255 char),
        INDICA_NOTIF_SINVIGENCIA varchar2(255 char),
        PERIODICIDAD varchar2(255 char),
        USR_REVISION_GA varchar2(255 char),
        ID_EMPRESA number(19,0),
        primary key (ID_CTRL_ALERTA)
    );

    create table SMI_EMPRESA (
        ID_EMPRESA number(19,0) not null,
        CODIGO_EMPRESA varchar2(255 char),
        FECHA_MODIFICA timestamp,
        ID_ESTADO number(19,0),
        ID_NIT_EMPRESA varchar2(255 char),
        NOMBRE_RAZON_SOCIAL varchar2(255 char),
        SIGLA_EMPRESA varchar2(255 char),
        USUARIO_MODIFICA varchar2(255 char),
        primary key (ID_EMPRESA)
    );
    
    create table SMI_EMPRESA_USUARIO (
    ID_USUARIO number(19,0) not null,
    ID_EMPRESA number(19,0) not null
    );

    create table SMI_PAGOPILA_COND_VEHI (
        ID_PAGOPILA_COND_VEHI number(19,0) not null,
        FECHA_FIN_VIGENCIA timestamp,
        FECHA_INICIO_VIGENCIA timestamp,
        ID_ESTADO number(1,0) not null,
        NRO_APROBACION_PAGO number(19,0),
        PERIODO_PAGO_PILA number(19,0),
        ID_CONDUCTOR number(19,0),
        ID_EMPRESA number(19,0),
        ID_VEHICULO number(19,0),
        primary key (ID_PAGOPILA_COND_VEHI)
    );

    create table SMI_PERSONA (
        ID_PERSONA number(19,0) not null,
        APELLIDOS varchar2(255 char),
        CELULAR number(19,0),
        CORREO_ELECTRONICO varchar2(255 char),
        DIRECCION varchar2(255 char),
        EXTENSION number(19,0),
        FECHA_NACIMIENTO timestamp,
        ID_ESTADO number(1,0),
        ID_GENERO number(19,0),
        NOMBRES varchar2(255 char),
        NUMERO_DOCUMENTO number(19,0),
        TIPO_DOCUMENTO number(19,0),
        primary key (ID_PERSONA)
    );

    create table SMI_USUARIO (
        ID_USUARIO number(19,0) not null,
        CLAVE_USUARIO varchar2(255 char),
        FECHA_MODIFICA timestamp not null,
        ID_ESTADO number(1,0) not null,
        LOGIN_USUARIO varchar2(255 char) not null,
        URL_SAFE_TOKEN varchar2(255 char),
        USR_MODIFICA varchar2(255 char),
        ID_TIPO_USUARIO number(19,0) not null,
        ID_PERFIL number(19,0),
        ID_PERSONA number(19,0) not null,
        primary key (ID_USUARIO)
    );

    alter table SMI_EMPRESA 
        add constraint UK_rgl9x6by5mwksrwcb4ys1af81 unique (CODIGO_EMPRESA);

    alter table SMB_APLICACION 
        add constraint FKd7ag6y9dw3116241lmbp0kio9 
        foreign key (ID_TIPO_APLICACION) 
        references SMB_TIPO_APLICACION;

    alter table SMB_AUDITORIA_SIRC 
        add constraint FKgm39v7tj0mqjj2ao5y6fgj4mg 
        foreign key (ID_CONDUCTOR) 
        references SMI_CONDUCTOR;

    alter table SMB_AUDITORIA_SIRC 
        add constraint FKcjubufub95j4st135x5gex4ol 
        foreign key (ID_EMPRESA) 
        references SMI_EMPRESA;

    alter table SMB_CONDUCTOR_VEHICULO 
        add constraint FK8461r6f18jsbu91u04xuwj3yw 
        foreign key (ID_CONDUCTOR) 
        references SMI_CONDUCTOR;

    alter table SMB_CONDUCTOR_VEHICULO 
        add constraint FKh5b155ufjpa3kemncfe0epmyr 
        foreign key (ID_EMPRESA) 
        references SMI_EMPRESA;

    alter table SMB_CONDUCTOR_VEHICULO 
        add constraint FK9poh5pgwq768d15f9leilrd7k 
        foreign key (ID_ESTADO) 
        references SMB_TARJETA_CONTROL_ESTADO;

    alter table SMB_FUNCIONARIO_SM 
        add constraint FKf6tq1rwr2wduity8y1y5lfpwp 
        foreign key (ID_PERSONA) 
        references SMI_PERSONA;

    alter table SMB_FUNCIONARIO_SM 
        add constraint FKgaxh47qjscmom8a2uac9x152 
        foreign key (ID_USUARIO) 
        references SMI_USUARIO;

    alter table SMB_OPCIONMENU 
        add constraint FKlsyeh7vi3m1by51hckhgeldgd 
        foreign key (ID_APLICACION) 
        references SMB_APLICACION;

    alter table SMB_PARAMETRO_SIMUR 
        add constraint FKtp9k7k2b41vrg2v7s1ws7r383 
        foreign key (ID_APLICACION) 
        references SMB_APLICACION;

    alter table SMB_PERFILXOPCION_MENU 
        add constraint FKt8fiw0jku4sc7qeosnd9h4ld0 
        foreign key (ID_OPCION_MENU) 
        references SMB_OPCIONMENU;

    alter table SMB_PERFILXOPCION_MENU 
        add constraint FKa3xke9f2wei8ghfc4cc451vdt 
        foreign key (ID_PERFIL) 
        references SMB_PERFIL;

    alter table SMI_CONDUCTOR 
        add constraint FKlfekq86otgs77a9m8li3hw2d3 
        foreign key (ID_PERSONA) 
        references SMI_PERSONA;

    alter table SMI_CONTROLALERTA 
        add constraint FKr8x6dr0wlmfojpnfmittfjn9c 
        foreign key (ID_EMPRESA) 
        references SMI_EMPRESA;
        
    alter table SMI_EMPRESA_USUARIO
    	add constraint FKkf660vymhqmtgxynp95up9h2p 
    	foreign key (ID_EMPRESA) 
    	references SMI_EMPRESA;
    	
	alter table SMI_EMPRESA_USUARIO 
		add constraint FKrodronq8nyhivhmxs00pd2fa 
		foreign key (ID_USUARIO) 
		references SMI_USUARIO;


    alter table SMI_PAGOPILA_COND_VEHI 
        add constraint FKfxt42h0636xt60oj6skb60jjr 
        foreign key (ID_CONDUCTOR) 
        references SMI_CONDUCTOR;

    alter table SMI_PAGOPILA_COND_VEHI 
        add constraint FKas2lhrypdehi3vbo9rwtyoo1q 
        foreign key (ID_EMPRESA) 
        references SMI_EMPRESA;

    alter table SMI_PAGOPILA_COND_VEHI 
        add constraint FKb1hwxng0s5ig9cx8qvimq8k4t 
        foreign key (ID_VEHICULO) 
        references SMB_CONDUCTOR_VEHICULO;

    alter table SMI_PERSONA 
        add constraint FKhgsc47i3nfxhhfx0k1ibibjxx 
        foreign key (TIPO_DOCUMENTO) 
        references SMB_TIPO_DOCUMENTO;

    alter table SMI_USUARIO 
        add constraint FK147uxyktq4p1l5ug97kahx5sq 
        foreign key (ID_TIPO_USUARIO) 
        references SMB_TIPO_USUARIO;

    alter table SMI_USUARIO 
        add constraint FKl905t5istdjm24luu7lynkcvu 
        foreign key (ID_PERFIL) 
        references SMB_PERFIL;

    alter table SMI_USUARIO 
        add constraint FKjxsigjuwwt2d1er8hrajg1n1d 
        foreign key (ID_PERSONA) 
        references SMI_PERSONA;
        
    commit;
