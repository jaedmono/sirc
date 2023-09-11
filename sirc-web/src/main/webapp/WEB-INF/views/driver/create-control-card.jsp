<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<base:basePage containerClass="home-container">
	<jsp:attribute name="footer">
        <style>
.error-select {
	border-bottom: 1px solid #F44336 !important;
	box-shadow: 0 1px 0 0 #F44336 !important;
}

.success-select {
	border-bottom: 1px solid #4CAF50 !important;
	box-shadow: 0 1px 0 0 #4CAF50 !important;
}

.error-input {
	border-bottom: 1px solid #F44336 !important;
	box-shadow: 0 1px 0 0 #F44336 !important;
}

.success-input {
	border-bottom: 1px solid #4CAF50 !important;
	box-shadow: 0 1px 0 0 #4CAF50 !important;
}

.error-file {
	border: 2px solid #F44336 !important;
}

.success-file {
	border: 1px solid #4CAF50 !important;
}
.btn-center {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100px;
}

input[type="date"]::before {
    color: #999999;
    content: attr(placeholder);
    font-size: 14px;
}
input[type="date"] {
    color: #ffffff;
    font-size: 14px;
}
input[type="date"]:focus,
input[type="date"]:valid {
    color: #666666;
    font-size: 14px;
}
input[type="date"]:focus::before,
input[type="date"]:valid::before {
    content: "" !important;
    font-size: 14px;
}

@media(min-width:768px){
    .panel-heading{
        display:none;
    }
    .panel{
        border:none;
        box-shadow:none;
    }
    .panel-collapse{
        height:auto;
        &.collapse{
            display:block;
        }
    }
}

@media(max-width:767px){
    .tab-content{
        .tab-pane{
            display:block;
        }
    }
    .nav-tabs{
        display:none;
    }
    .panel-title a{
        display:block;
    }
    .panel{
        margin:0;
        box-shadow:none;
        border-radius:0;
        margin-top:-2px;
    }
    .tab-pane{
        &:first-child{
            .panel{
                border-radius:5px 5px 0 0;
            }
        }
        &:last-child{
            .panel{
                border-radius:0 0 5px 5px;
            }
        }
    }
}



</style>
        <script
                src="${pageContext.request.contextPath}/resources/js/dev/company.js"></script>
        <script
                src="${pageContext.request.contextPath}/resources/js/dev/driver/form-validations.js"></script>
        <script
			src="${pageContext.request.contextPath}/resources/js/dev/driver/create-control-card.js"></script>
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
    </jsp:attribute>
	<jsp:body>
        <spring:message code="common.button.text.accept"
			var="labelAccept" />
        <spring:message code="common.button.text.deny" var="labelDeny" />
        <spring:message code="message.cancel.title.targetcontrol"
			var="colTextTitleConfirmCancel" />
        <spring:message code="message.cancel.title.process"
			var="colTextTitleCancelProcess" />
        <input id="url-home-rest" type="hidden"
			value="${constants.general.homeMapping}">
        <div class="jumbotron">
            <form:form id="formDriver" commandName="driverDTO"
				class="form-validation" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="id" id="id"
					value="${driverDTO.id}">
                <input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
                <input type="hidden" name="idFactorCalidad"
					id="idFactorCalidad" value="${driverDTO.idFactorCalidad}">
                <input type="hidden" name="idEmpresa"
                       id="idEmpresa"value="${driverDTO.idEmpresa}"/>
                <input type="hidden" name="numeroTarjetaControl"
                       id="numeroTarjetaControl" value="${driverDTO.numeroTarjetaControl}">
                <input type="hidden" name="plateValidated"
                       id="plateValidated" value="${plateValidated}">
                <input type="hidden" name="tipoTramite" id="tipoTramite" value="${tipoTramite}" />
                <ul  class="nav nav-tabs nav-justified"  >
                    <li class="nav-item active">
                        <a id="tab-driver" href="#driverData" role="tab" data-toggle="tab" >
                            <h4>Datos Básicos del Conductor</h4>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a id="tab-vehicle" href="#vehicleData" role="tab" data-toggle="tab" >
                            <h4>Datos Básicos del Vehículo</h4>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a id="tab-manage-card" href="#manageCardData" role="tab" data-toggle="tab"  >
                            <h4>Gestión Tarjetas de Control</h4>
                        </a>
                    </li>
                </ul>
                <div class="tab-content" >
                    <div class="tab-pane active" id="driverData">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent=".tab-pane" href="#collapseDriver">
                                        Datos Básicos del Conductor
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseDriver" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="container">
                                            <h3>Datos básicos del conductor</h3>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div
                                                            class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    name="conductorDTO.persona.nombres" id="first_name"
                                                                    type="text" data-parsley-maxlength="200" class="form-control"
                                                                    value="${driverDTO.conductorDTO.persona.nombres}"
                                                                    placeholder="Nombres" required>
                                                            <label class="fa fa-user"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div
                                                            class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    name="conductorDTO.persona.apellidos" id="last_name"
                                                                    type="text" data-parsley-maxlength="100" class="form-control noscroll"
                                                                    value="${driverDTO.conductorDTO.persona.apellidos}"
                                                                    placeholder="Apellidos" required>
                                                            <label class="fa fa-user"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12"
                                                     id="divFechaNacimiento">
                                                    <div
                                                            class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    placeholder="Fecha nacimiento"
                                                                    class="form-control"
                                                                    name="conductorDTO.persona.fechaNacimiento" type='text'
                                                                    id="fechaNacimiento" required
                                                                    value="<fmt:formatDate value="${driverDTO.conductorDTO.persona.fechaNacimiento}" pattern="dd/MM/YYYY"/>" />
                                                            <label class="fa fa-calendar-o"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-12" style="padding: 0px;">
                                                        <div id="divFoto" class="col-md-3">
                                                            <div class="input-group">
                                                                <div class="icon-addon addon-md">
                                                                    <input type="text" disabled
                                                                           class="form-control" id="loadDocument"
                                                                           placeholder="Cargar foto" readonly>
                                                                    <label class="fa fa-picture-o"></label>
                                                                </div>
                                                                <label class="input-group-btn">
                                                                <span id="btn-examinar"
                                                                      class="btn btn-info">Cargar
                                                                    <input type="file"
                                                                           style="display: none;" id="picture-profile" name="file"
                                                                           accept="image/*">
                                                                </span>
                                                                </label>
                                                            </div>
                                                            <div style="padding: 10px;  margin-bottom: 5px; text-align: justify;">
                                                                <span>La imagen no debe sobrepasar los 300kb.</span>
                                                                <span style="color: #000; font-weight: bold; text-decoration: underline;">La extensión de la imagen debe ser .jpg</span>
                                                                <span> la foto debe ser tamaño 3 X 4 cm</span>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div style="clear: both">
                                                                <input type="hidden" id="fotoConductor"
                                                                       name="conductorDTO.foto" value="${driverDTO.conductorDTO.foto}">
                                                                <c:choose>
                                                                    <c:when
                                                                            test="${not empty driverDTO.conductorDTO.uriFoto}">
                                                                        <img id="previewPicture"
                                                                             frameborder="0" scrolling="no" width="120px" height="150px"
                                                                             src="${contextPath}/get-img?id=${driverDTO.conductorDTO.id}"></img>
                                                                    </c:when>
                                                                    <c:when
                                                                            test="${not empty driverDTO.conductorDTO.foto}">
                                                                        <img id="previewPicture"
                                                                             frameborder="0" scrolling="no" width="120px" height="150px"
                                                                             src="data:image/JPEG;base64,${driverDTO.conductorDTO.foto}"></img>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <iframe id="previewPicture"
                                                                                frameborder="0" scrolling="no" width="120px" height="150px"></iframe>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <br />
                                                        </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="icon-addon addon-md">
                                                        <select id="tipoIdentificacion"
                                                                name="conductorDTO.persona.tipoIdentificacion"
                                                                class="form-control" placeholder="Tipo documento">
                                                            <option value="" disabled
                                                                    selected>Tipo documento</option>
                                                            <c:forEach var="tipoDocumento"
                                                                       items="${listTipoDocumentoDTO}">
                                                                <option
                                                                        value="${tipoDocumento.id}"
                                                                    ${driverDTO.conductorDTO.persona.tipoIdentificacion==tipoDocumento.id?"selected":""}>${tipoDocumento.descripcionTipoDocumento}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-list-alt"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    name="conductorDTO.persona.numeroIdentificacion"
                                                                    id="identification_number" type="number" min=0
                                                                    max="9999999999" class="form-control noscroll"
                                                                    value="${driverDTO.conductorDTO.persona.numeroIdentificacion}"
                                                                    placeholder="Nro. documento" required>
                                                            <label class="fa fa-barcode"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12"
                                                     id="divFechaExpedicionDocumento">
                                                    <div
                                                            class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    placeholder="Fecha exp. doc. " class="form-control"
                                                                    name="conductorDTO.persona.fechaExpedicionDocumento"
                                                                    type='text' id="fechaExpedicionDocumento" required
                                                                    value="<fmt:formatDate value="${driverDTO.conductorDTO.persona.fechaExpedicionDocumento}" pattern="dd/MM/YYYY"/>" />
                                                            <label class="fa fa-calendar-o"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-8">
                                                    <div class="icon-addon addon-md">
                                                        <select id="grupoSanguineo"
                                                                name="conductorDTO.grupoSanguineo"
                                                                placeholder="Grupo Sanguineo" class="form-control">
                                                            <option value="" disabled
                                                                    selected>Grupo sanguineo</option>
                                                            <option value="A"
                                                                ${driverDTO.conductorDTO.grupoSanguineo.equals("A")?"selected":""}>A</option>
                                                            <option value="B"
                                                                ${driverDTO.conductorDTO.grupoSanguineo.equals("B")?"selected":""}>B</option>
                                                            <option value="AB"
                                                                ${driverDTO.conductorDTO.grupoSanguineo.equals("AB")?"selected":""}>AB</option>
                                                            <option value="O"
                                                                ${driverDTO.conductorDTO.grupoSanguineo.equals("O")?"selected":""}>O</option>
                                                        </select>
                                                        <label class="fa fa-heart"></label>
                                                    </div>
                                                </div>
                                                <div class="col-md-4"
                                                     style="padding-left: 0px;">
                                                    <div class="icon-addon addon-md">
                                                        <select id="rh"
                                                                name="conductorDTO.factorRh" placeholder="RH"
                                                                class="form-control">
                                                            <option value="" disabled
                                                                    selected>RH</option>
                                                            <option value="+"
                                                                ${driverDTO.conductorDTO.factorRh.equals("+")?"selected":""}>+</option>
                                                            <option value="-"
                                                                ${driverDTO.conductorDTO.factorRh.equals("-")?"selected":""}>-</option>
                                                        </select>
                                                        <label class="fa fa-tint"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div
                                                            class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    name="conductorDTO.persona.celular" id="phone_number"
                                                                    type="number" max="9999999999" min=0
                                                                    value="${driverDTO.conductorDTO.persona.celular}"
                                                                    class="form-control noscroll"
                                                                    placeholder="Número de télefono 6034574466" required>
                                                            <label
                                                                    class="fa fa-phone-square"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label for="telephone">Fijo</label>
                                                    <input name="group1" type="radio"
                                                           id="telephone" value="telefono"
                                                        ${driverDTO.tipoTelFijo == true ? 'checked':''} />
                                                </div>
                                                <div class="col-md-6" style="padding: 0px;">
                                                    <label for="cell_phone">Celular</label>
                                                    <input name="group1" type="radio" maxlength="10"
                                                           id="cell_phone" value="celular"
                                                        ${driverDTO.tipoTelFijo == false ? 'checked':''} />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div
                                                            class="form-group eq-ui-input-field">
                                                        <div class="icon-addon addon-md">
                                                            <input
                                                                    name="conductorDTO.persona.direccion" id="home_address"
                                                                    type="text" data-parsley-maxlength="100"
                                                                    value="${driverDTO.conductorDTO.persona.direccion}"
                                                                    class="form-control" placeholder="Dirección de domicilio"
                                                                    required>
                                                            <label class="fa fa-home"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="icon-addon addon-md">
                                                        <select name="conductorDTO.idEps"
                                                                class="form-control" id="eps" placeholder="EPS">
                                                            <option value="" disabled
                                                                    selected>Seleccione la EPS</option>
                                                            <c:forEach var="eps"
                                                                       items="${listEpsDTO}">
                                                                <option value="${eps.idEps}"
                                                                    ${driverDTO.conductorDTO.idEps==eps.idEps?"selected":""}>${eps.nombreEps}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-hospital-o"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="icon-addon addon-md">
                                                        <select name="conductorDTO.idAfp"
                                                                class="form-control" id="afp" placeholder="Fondo de Pensiones">
                                                            <option value="" disabled
                                                                    selected>Seleccione el fondo de pensiones</option>
                                                            <c:forEach var="afp"
                                                                       items="${listAfpDTO}">
                                                                <option value="${afp.idAfp}"
                                                                    ${driverDTO.conductorDTO.idAfp==afp.idAfp?"selected":""}>${afp.nombreAfp}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-building-o"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="icon-addon addon-md">
                                                        <select name="conductorDTO.idArl"
                                                                class="form-control" id="arl" placeholder="ARL">
                                                            <option value="" disabled
                                                                    selected>Seleccione la ARL</option>
                                                            <c:forEach var="arl"
                                                                       items="${listArlDTO}">
                                                                <option value="${arl.idArl}"
                                                                    ${driverDTO.conductorDTO.idArl==arl.idArl?"selected":""}>${arl.nombreArl}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-user-md"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="container">
                                            <h3>Comprobante de pago de seguridad social</h3>
                                            <div class="col-md-12"
                                                 style="padding: 0px; margin-top: -5px; margin-bottom: 10px; text-align: justify;">
                                                <span style="color: #000; font-weight: bold;">Decreto Nacional 1079 del 2015, Artículo 2.2.1.3.8.11:</span>
                                                <span style="font-style: oblique;">“De conformidad con el artículo 34 de la Ley 336 de 1996, la empresa deberá constatar que el conductor se encuentra afiliado al Sistema de Seguridad Social como cotizante y que en el sistema se han pagado efectiva y oportunamente los aportes”.</span>
                                            </div>
                                        </div>
                                    </div>

                                    <c:forEach var="planilla" items="${driverDTO.planillas}" varStatus="status">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="icon-addon addon-md">
                                                    <form:select name="idOperadorPila" path="planillas[${status.index}].operadorPila"
                                                                 id="idOperadorPila" class="form-control" placeholder="Operador de recaudo">
                                                        <form:option value="" label="Seleccione el operador de recaudo" />
                                                        <form:options items="${listOperadoresPilaDTO}" itemLabel="nombre" itemValue="idOperador" />
                                                    </form:select>
                                                    <label class="fa fa-university"></label>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="row">
                                                    <div class="col-md-12" id="divPeriodoOne">
                                                        <div class="form-group eq-ui-input-field">
                                                            <div class="icon-addon addon-md">
                                                                <form:input placeholder="Periodo Pago (mm/aaaa)" class="form-control"
                                                                            name="periodoPago" path="planillas[${status.index}].stringPeriodoPago"
                                                                            type='text' id="driver_payment_period" value="${planilla.stringPeriodoPago}" />
                                                                <label class="fa fa-calendar-o"></label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="form-group eq-ui-input-field">
                                                            <div class="icon-addon addon-md">
                                                                <form:input placeholder="No. Planilla" name="nroPagoPila" id="approbal_number" max="9999999999999" type="number"
                                                                            class="form-control noscroll" path="planillas[${status.index}].numeroAprobacion"
                                                                            value="${planilla.numeroAprobacion}" required="true" maxlength="12" />
                                                                <label class="fa fa-barcode"></label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="col-md-6">
                                                <button type="button" id="btn-agregar-planilla"
                                                        class="btn btn-info eq-ui-waves-light">Agregar </button>
                                            </div>
                                            <div class="col-md-6">
                                                <button id="btn-eliminar-planilla" type="button"
                                                        class="btn btn-info eq-ui-waves-light" disabled >Eliminar
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="btn-center">
                                            <button type="button" id="btn-next-driver"
                                                    class="btn btn-info eq-ui-waves-light ">Siguiente </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="vehicleData">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent=".tab-pane" href="#collapseVehicle">
                                        Datos Básicos del Vehículo
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseVehicle" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="container">
                                            <h3>Datos del vehículo</h3>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input name="placa" id="car_id" pattern="[A-Za-z]{3}[0-9]{3}"
                                                                   type="text" data-parsley-maxlength="6"
                                                                   value="${driverDTO.placa}" class="form-control"
                                                                   placeholder="Placa del vehículo" required>
                                                            <label class="fa fa-car"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group icon-addon addon-md">
                                                        <select name="empresaDTO.id"
                                                                id="codigoEmpresa" class="form-control"
                                                                placeholder="Nombre o Razón Social">
                                                            <option value="" disabled
                                                                    selected>Seleccione la empresa</option>
                                                            <c:forEach var="empresa"
                                                                       items="${listEmpresaDTO}">
                                                                <option value="${empresa.id}" data-rs="${empresa.codigoEmpresa}"
                                                                        data-nit="${empresa.nit}"
                                                                    ${driverDTO.empresaDTO.id==empresa.id?"selected":""}>${empresa.razonSocial}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-building"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="company_razonSocial"
                                                                   type="text" value="${driverDTO.empresaDTO.codigoEmpresa}"
                                                                   name="empresaDTO.codigoEmpresa" class="form-control"
                                                                   placeholder="Código" readonly>
                                                            <label class="fa fa-barcode"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="nit" type="text"
                                                                   name="empresaDTO.nit" value="${driverDTO.empresaDTO.nit}"
                                                                   class="form-control" placeholder="NIT" readonly>
                                                            <label class="fa fa-barcode"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3" style="padding-right: 0px;">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="fechaVencimientoSoat"
                                                                   type="text" name="fechaVencimientoSoat" class="form-control"
                                                                   placeholder="Fecha ven. SOAT " required
                                                                   value="<fmt:formatDate value="${driverDTO.fechaVencimientoSoat}" pattern="dd/MM/YYYY"/>">
                                                            <label class="fa fa-calendar-o"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="nroSOAT" type="text"
                                                                   name="nroSOAT" value="${driverDTO.nroSOAT}"
                                                                   class="form-control" placeholder="Número SOAT" required>
                                                            <label class="fa fa-barcode"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3" style="padding-right: 0px;">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="fechaVencimientoRtm"
                                                                   type="text" name="fechaVencimientoRtm" class="form-control"
                                                                   placeholder="Fecha ven. RTM " required
                                                                   value="<fmt:formatDate value="${driverDTO.fechaVencimientoRtm}" pattern="dd/MM/YYYY"/>">
                                                            <label class="fa fa-calendar-o"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="nroRTM" type="text"
                                                                   name="nroRTM" value="${driverDTO.nroRTM}"
                                                                   class="form-control" placeholder="Número RTM" required>
                                                            <label class="fa fa-barcode"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="nroTarjetaOperacion"
                                                                   type="text" name="nroTarjetaOperacion"
                                                                   value="${driverDTO.nroTarjetaOperacion}"
                                                                   class="form-control" placeholder="Número tarjeta operación"
                                                                   required>
                                                            <label class="fa fa-barcode"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3" style="padding-left: 0px;">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="eq-ui-input-field">
                                                        <div class="form-group icon-addon addon-md">
                                                            <input id="fechaVencimientoTO"
                                                                   type="text" name="fechaVencimientoTO" class="form-control"
                                                                   placeholder="Fecha ven. TO " required
                                                                   value="<fmt:formatDate value="${driverDTO.fechaVencimientoTO}" pattern="dd/MM/YYYY"/>">
                                                            <label class="fa fa-calendar-o"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-md-3">
                                            <div class="row">
                                                <div class="col-md-12" style="margin-top: 7px;">
                                                    <div class="checkboxfqa">
                                                        <label>
                                                            <input id="factorCalidad"
                                                                   name="factorCalidad" type="checkbox" disabled
                                                                   value="${driverDTO.factorCalidad}"
                                                                ${driverDTO.factorCalidad == true ? 'checked':''}>
                                                            <i class="factorqa"></i> Factor Calidad Vehículo
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="icon-addon addon-md">
                                                        <select name="idMetodoCobro" id="idMetodoCobro" class="form-control" placeholder="Método de cobro">
                                                            <option value="" disabled selected>Seleccione el método de cobro</option>
                                                            <c:forEach var="metodocobro" items="${listMetodoCobroDTO}">
                                                                <option value="${metodocobro.idMetodoCobro}"
                                                                    ${driverDTO.idMetodoCobro==metodocobro.idMetodoCobro?"selected":""}>${metodocobro.descripcion}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-money"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="icon-addon addon-md">
                                                        <select name="idTipoServicio" id="idTipoServicio" class="form-control" placeholder="Tipo de servicio">
                                                            <option value="" disabled selected>Seleccione el tipo de servicio</option>
                                                            <c:forEach var="tiposervicio" items="${listTipoServicioDTO}">
                                                                <option value="${tiposervicio.idTipoServicio}"
                                                                    ${driverDTO.idTipoServicio==tiposervicio.idTipoServicio?"selected":""}>${tiposervicio.descripcion}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="fa fa-bar-chart"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="btn-center">
                                            <button type="button" id="btn-next-vehicle"
                                                    class="btn btn-info eq-ui-waves-light ">Siguiente </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="manageCardData">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent=".tab-pane" href="#collapseCard">
                                        Gestión Tarjetas de Control
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseCard" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <div class="row" style="text-align: right;">
                                        <div class="btn-center">
                                            <button type="button" id="btn_save"
                                                    class="btn btn-info eq-ui-waves-light button-validation"
                                                    value=""></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden"
						value="${driverDTO.id!=0?driverDTO.id:0}" />
                <input type="hidden" name="idConductor"
						value="${driverDTO.idConductor}" />
                <input type="hidden" id="typeTransaction"
                       value="${typeTransaction}" />
            
			</form:form>

        </div>

    </jsp:body>
</base:basePage>