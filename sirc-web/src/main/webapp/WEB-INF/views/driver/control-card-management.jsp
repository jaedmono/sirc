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
            .create-control-card {
                display: flex;
                justify-content: center;
                align-items: center;
            }
        </style>
        <script
                src="${pageContext.request.contextPath}/resources/js/dev/company.js"></script>
        <script
                src="${pageContext.request.contextPath}/resources/js/dev/driver/control-card-management.js"></script>
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
  </jsp:attribute>
  <jsp:body>
      <spring:message code="common.button.text.accept"
                      var="labelAccept" />
      <spring:message code="common.button.text.deny"
                      var="labelDeny" />
      <spring:message code="message.cancel.title.targetcontrol"
                      var="colTextTitleConfirmCancel" />
      <spring:message code="message.cancel.title.process"
                      var="colTextTitleCancelProcess" />
      <input id="url-home-rest" type="hidden"
             value="${constants.general.homeMapping}">
      <div class="jumbotron">
          <form:form id="formConsultarTarjeta" commandName="driverDTO" action="consultar-tarjeta"
                     class="form-validation" method="GET" enctype="multipart/form-data">
              <input type="hidden" name="id" id="id"
                     value="${driverDTO.id}">
              <input type="hidden" name="idFactorCalidad"
                     id="idFactorCalidad" value="${driverDTO.idFactorCalidad}">
              <input type="hidden" name="tipoTransaccion"
                     id="tipoTransaccion" value="${driverDTO.tipoTransaccion}">
              <input type="hidden" name="idEmpresa"
                     id="idEmpresa"value="${driverDTO.idEmpresa}"/>
              <input type="hidden" name="idConductor"	id="idConductor" value="${driverDTO.idConductor}" />
              <input type="hidden" name="tipoTramite" id="tipoTramite" value="${tipoTramite}" />
              <div class="row">
                  <div class="row">
                      <div class="col-md-4">
                          <span style="font-style: oblique;">Ingrese el número único de la Tarjeta de Control a gestionar </span>
                      </div>
                      <div class="col-md-8">
                          <div class="col-md-10">
                              <div
                                      class="form-group eq-ui-input-field">
                                  <div class="icon-addon addon-md">
                                      <input
                                              name="numeroTarjetaControl" id="control_card_number"
                                              type="text" class="form-control"
                                              value="${driverDTO.numeroTarjetaControl}"
                                              placeholder="Número de tarjeta de control" required>
                                      <label class="fa fa-barcode"></label>
                                  </div>
                                  <input type="hidden" id="tipoTransaccionConsul"
                                         name="tipoTransaccion" value="CONSULTA">
                              </div>
                          </div>
                          <div class="col-md-2">
                              <button type="submit"
                                      id="btn-consultarT"
                                      class="btn btn-info eq-ui-waves-light button-validation"
                                      form="formConsultarTarjeta" >Consultar
                              </button>
                          </div>
                      </div>
                  </div>
                  <div clas="row">
                      <div class="col-md-3"></div>
                      <div class="col-md-9 create-control-card">
                          <a id="newCardLink"><spring:message code="header.administration.control.card.manage" /></a>
                      </div>
                  </div>
              </div>
              <div id="manageCardData" class="hidden">
                  <div class="row" style="text-align: center;">
                      <div style="padding: 50px;">
                          <button type="button" id="btn_save"
                                  class="btn btn-info eq-ui-waves-light button-validation"
                                  value=""></button>
                          <button type="button" id="btn_cancel"
                                  class="btn btn-info eq-ui-waves-light button-validation"
                                  data-toggle="modal" >Cancelar Tarjeta de Control</button>
                      </div>
                  </div>
              </div>
          </form:form>
          <!--confirmar cancelacion tarjeta -->
          <div id="cancelTarget" class="modal fade" role="dialog">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close"
                                  data-dismiss="modal">&times;</button>
                          <h4 class="mdl-dialog__title">${colTextTitleConfirmCancel}</h4>
                      </div>
                      <div class="modal-body">
                          <div id="page-modal-container"
                               class="col-xs-12">
                              <jsp:include
                                      page="../common/page-message.jsp" />
                          </div>
                          <!-- page message -->
                          <div class="row">
                              <div class="col-xs-8">
                                  <div
                                          class="form-group eq-ui-input-field">
                                      <spring:message
                                              code="message.cancel.targetcontrol" />
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="modal-footer">
                          <button type="button"
                                  class="btn btn-info eq-ui-waves-light button-validation"
                                  data-dismiss="modal" id="btn_cancel_target" data-row="">${labelAccept}</button>
                          <button type="button"
                                  class="btn btn-danger eq-ui-waves-light" data-dismiss="modal">${labelDeny}</button>
                      </div>
                  </div>
              </div>
          </div>
      </div>

  </jsp:body>
</base:basePage>
