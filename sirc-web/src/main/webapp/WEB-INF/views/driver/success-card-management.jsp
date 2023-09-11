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
            .btn-center {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100px;
            }
        </style>
        <script
                src="${pageContext.request.contextPath}/resources/js/dev/company.js"></script>
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
            <form:form id="formDriver" commandName="driverDTO" action="control-card-management"
                     class="form-validation" method="GET" enctype="multipart/form-data">
            <div class="row">
                <div class="row">
                    <div class="col-md-4 " style="text-align: center;">
                        <span>Número único <br/> Tarjeta de Control</span>
                    </div>
                    <div class="col-md-8">
                        <div class="col-md-10">
                            <div class="form-group eq-ui-input-field">
                                <div class="icon-addon addon-md">
                                    <input
                                            name="numeroTarjetaControl" id="control_card_number"
                                            type="text" class="form-control"
                                            value="${pageQrLink}"
                                            placeholder="Número de tarjeta de control" >
                                    <label class="fa fa-barcode"></label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row" style="text-align: center; padding-bottom: 2em;">
                    <div class="form-group eq-ui-input-field well">
                        <div class="row"><span>Su tarjeta de control con el número ${pageQrLink} ha sido registrada en el sistema bajo el estado</span></div>
                        <div class="row"><span>${typeTransaction}</span></div>
                        <div class="row"><span>La fecha de vigencia del documento es: ${fechaVigenciaTarjeta}</span></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-5 " style="text-align: right;">
                            <img id="previewQrPicture"
                                 frameborder="0" scrolling="no" width="80px" height="80px"
                                 src="${contextPath}/get-qr-img?id=${pageQrLink}"/>
                    </div>
                    <div class="col-md-3 " style="text-align: center;">
                        <div class="row"><span style="font-style: oblique;"> Para efectos de control adicione este</span></div>
                        <div class="row"><span style="font-style: oblique;"> código QR en la Tarjeta de control física</span></div>
                        <div class="row">
                            <a class="btn btn-info eq-ui-waves-light"
                                href='<spring:url value="download-qr-image/${pageQrLink}"></spring:url>'><i class="fa fa-download"></i> Descargar Código QR</a>
                        </div>
                    </div>
                </div>
                <div class="row">
	                <div class="btn-center">
	                    <button type="submit" id="btn_manage_card"
	                            class="btn btn-info eq-ui-waves-light button-validation"
	                            value="">Gestionar una Nueva Tarjeta de Control</button>
	                </div>
                </div>

                <input type="hidden"
                       value="${driverDTO.id!=0?driverDTO.id:0}" />
                <input type="hidden" name="idConductor"
                       value="${driverDTO.idConductor}" />
                <input type="hidden" id="typeTransaction"
                       value="${typeTransaction}" />
            </div>
            </form:form>

        </div>
    </jsp:body>
</base:basePage>