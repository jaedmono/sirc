<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<base:basePage containerClass="home-container">
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/resources/js/dev/cargar/eliminar-factor-calidad.js"></script>
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
    </jsp:attribute>
    <jsp:body>
        <spring:message code="header.administration.factorcalidad.modificar" var="textTitle" />
        <div class="jumbotron">
            <div class="container">
                <h2>${textTitle}</h2>
            </div>
            <div id="divConsultarFQ" class="row" style="margin-top: 15px;">
                <form id="formConsultarFactorCalidad" action="consultar-factor-calidad-vehiculo" class="form-util" method="GET">
                    <div class="col-md-6">
                        <div class="col-md-10">
                            <div class="form-group eq-ui-input-field">
                                <div class="icon-addon addon-md">
                                    <input id="nroPlaca" name="nroPlaca" type="text" class="form-control" 
                                           placeholder="Número placa" required>
                                    <label class="fa fa-taxi"></label>
                                </div>										
                            </div>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" id="btn-consultar-factor-qa" class="btn btn-info eq-ui-waves-light button-validation">
                                Consultar factor calidad
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            
            
            <div class="row" style="margin-top: 15px;">
                <form id="formEliminarFactorCalidad" commandName="factorCalidadDTO" class="form-util">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <input id="idFactorQa" name="id" type="hidden" value="${factorCalidadDTO.id}">
                    <div id="divFactorQa" class="col-md-12" style="display: none;">
                        <div class="col-md-3">
                            <div class="form-group eq-ui-input-field">
                                <label>Número placa</label>
                                <div class="icon-addon addon-md">
                                    <input name="placa" type="text" class="form-control" value="${factorCalidadDTO.placa}" disabled="false" required>
                                    <label class="fa fa-taxi"></label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group eq-ui-input-field">
                                <label>Razón Social Empresa</label>
                                <div class="icon-addon addon-md">
                                    <input type="text" class="form-control" value="${factorCalidadDTO.razonSocialEmpresa}" disabled="false" required>
                                    <label class="fa fa-building"></label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group eq-ui-input-field">
                                <label>Código SDM</label>
                                <div class="icon-addon addon-md">
                                    <input type="text" class="form-control" value="${factorCalidadDTO.codSDMEmpresa}" disabled="false" required>
                                    <label class="fa fa-hashtag"></label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12" style="margin-top: 10px;">
                            <button type="button" id="btnCancelar" class="btn btn-info">
                                Cancelar
                            </button>
                            <button type="submit" id="btn-eliminar-factor-qa" class="btn btn-danger eq-ui-waves-light button-validation">
                                Eliminar marca factor calidad
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>

</base:basePage>