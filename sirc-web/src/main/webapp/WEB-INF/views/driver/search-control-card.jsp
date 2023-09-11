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
            th, td { white-space: nowrap; }
            div.dataTables_wrapper {
                margin: 0 auto;
            }
            .btn-full-size{
                width: 100%;
            }

        </style>
        <script
			src="${pageContext.request.contextPath}/resources/js/dev/driver/control-card-search.js"></script>
        <script
                src="https://github.com/carlo/jquery-base64/blob/master/jquery.base64.min.js"></script>

    </jsp:attribute>
	<jsp:body>
        <spring:message code="common.button.text.accept" var="labelAccept" />
        <spring:message code="common.button.text.deny" var="labelDeny" />
        <spring:message code="message.cancel.title.targetcontrol" var="colTextTitleConfirmCancel" />
        <spring:message code="message.cancel.title.process" var="colTextTitleCancelProcess" />
        <input id="url-home-rest" type="hidden"
			value="${constants.general.homeMapping}">
        <div class="jumbotron">
            <form:form id="formDriver" commandName="driverDTO"
				class="form-validations" method="GET" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
                <div class="row">
                    <div class="container">
                        <h3>Consultar Tarjetas de Control</h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="row">
                            <div class="col-md-12" id="divIssueDate">
                                <div class="form-group eq-ui-input-field">
                                    <div class="icon-addon addon-md">
                                        <input
                                                placeholder="Fecha expedición. dd/mm/aaaa" class="form-control"
                                                name="fechaValidez"
                                                type='text' id="issuesDate" required/>
                                        <label class="fa fa-calendar-o"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="row">
                            <div class="col-md-12" id="divExpiryDate">
                                <div  class="form-group eq-ui-input-field">
                                    <div class="icon-addon addon-md">
                                        <input  placeholder="Fecha Vigencia. dd/mm/aaaa" class="form-control"
                                                name="fechaVigencia"
                                                type='text' id="expiryDate"/>
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
                                        <input id="car_id" name="placa"
                                               type="text" data-parsley-maxlength="6" class="form-control"
                                               placeholder="Placa del vehículo" required>
                                        <label class="fa fa-taxi"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group icon-addon addon-md">
                                    <select id="controlCardStatus" class="form-control" name="idEstado">
                                        <option value="" disabled selected>Seleccione el estado</option>
                                        <c:forEach var="status" items="${listCardStatusDTO}">
                                            <option value="${status.id}" >${status.description}</option>
                                        </c:forEach>
                                    </select>
                                    <label class="fa fa-tasks"></label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-7">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group icon-addon addon-md">
                                    <select name="empresaDTO.id"
                                            id="codigoEmpresa" class="form-control"
                                            placeholder="Nombre o Razón Social">
                                        <option value="" disabled selected>Seleccione la empresa</option>
                                        <c:forEach var="empresa" items="${listEmpresaDTO}">
                                            <option value="${empresa.id}" >${empresa.razonSocial}</option>
                                        </c:forEach>
                                    </select>
                                    <label class="fa fa-building"></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group eq-ui-input-field">
                                    <div class="icon-addon addon-md">
                                        <input  id="identification_number" type="number" min=0
                                                max="999999999999999" class="form-control noscroll"
                                                name="conductorDTO.persona.numeroIdentificacion"
                                                placeholder="Nro. documento" required>
                                        <label class="fa fa-id-card"></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <button type="button"
                                id="btn-search"
                                class="btn btn-info eq-ui-waves-light button-validation btn-full-size" >Consultar</button>
                    </div>
                </div>
			</form:form>
            <div class="well well-sm"></div>
            <div class="row">
                <div class="col-md-2">
                    <button onclick="exportTableToExcel()"
                            class="btn btn-info eq-ui-waves-light button-validation btn-full-size">Exportar a Excel</button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <!--  Table -->
                    <div
                            class="eq-ui-card eq-ui-card-with-table table table-striped table-bordered table-responsive">
                        <table id="controlCardSearch"
                               class="table eq-ui-data-table eq-ui-centered z-depth-1" style="width:100%">
                            <thead>
                            <tr>
                                <th class="eq-ui-data-table-cell-non-numeric">Tarjeta de control</th>
                                <th>Estado Tarjeta</th>
                                <th data-type="date" data-format-string="DD/MM/YYYY">Fecha Vigencia</th>
                                <th>Fecha Expedición</th>
                                <th>Tipo Transacción</th>
                                <th style="width: 30px;">Empresa que Expide</th>
                                <th>Nombres</th>
                                <th>Apellidos</th>
                                <th>No. Documento</th>
                                <th>Telefono</th>
                                <th>Placa</th>
                                <th>Número SOAT</th>
                                <th>Fecha Vigencia SOAT</th>
                                <th>Número RTM</th>
                                <th>Fecha Vigencia RTM</th>
                                <th>Número Tarjeta Operación</th>
                                <th>Fecha Vigencia Tarjeta Operación</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${controlCards}" var="card">
                                <tr>
                                    <td>${card.tarjetaControl}</td>
                                    <td>${card.estado}</td>
                                    <td>${card.fechaVigencia}</td>
                                    <td>${card.fechaExpedicion}</td>
                                    <td>${card.tipoTransaccion}</td>
                                    <td>${card.empresa}</td>
                                    <td>${card.nombres}</td>
                                    <td>${card.apellidos}</td>
                                    <td>${card.numeroDocumento}</td>
                                    <td>${card.telefono}</td>
                                    <td>${card.placa}</td>
                                    <td>${card.numeroSoat}</td>
                                    <td>${card.fechaVigenciaSOAT}</td>
                                    <td>${card.numeroRTM}</td>
                                    <td>${card.fechaVigenciaRTM}</td>
                                    <td>${card.numeroTarjetaOperacion}</td>
                                    <td>${card.fechaVigenciaTarjetaOperacion}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        <div type="hidden">
            <table id="dataToExport" style="visibility:hidden">
                <tr>
                    <th >Tarjeta de control</th>
                    <th>Estado Tarjeta</th>
                    <th >Fecha Vigencia</th>
                    <th>Fecha Expedición</th>
                    <th>Tipo Transacción</th>
                    <th >Empresa que Expide</th>
                    <th>Nombres</th>
                    <th>Apellidos</th>
                    <th>No. Documento</th>
                    <th>Telefono</th>
                    <th>Placa</th>
                    <th>Número SOAT</th>
                    <th>Fecha Vigencia SOAT</th>
                    <th>Número RTM</th>
                    <th>Fecha Vigencia RTM</th>
                    <th>Número Tarjeta Operación</th>
                    <th>Fecha Vigencia Tarjeta Operación</th>
                </tr>
                <c:forEach items="${controlCards}" var="card">
                    <tr>
                        <td>${card.tarjetaControl}</td>
                        <td>${card.estado}</td>
                        <td>${card.fechaVigencia}</td>
                        <td>${card.fechaExpedicion}</td>
                        <td>${card.tipoTransaccion}</td>
                        <td>${card.empresa}</td>
                        <td>${card.nombres}</td>
                        <td>${card.apellidos}</td>
                        <td>${card.numeroDocumento}</td>
                        <td>${card.telefono}</td>
                        <td>${card.placa}</td>
                        <td>${card.numeroSoat}</td>
                        <td>${card.fechaVigenciaSOAT}</td>
                        <td>${card.numeroRTM}</td>
                        <td>${card.fechaVigenciaRTM}</td>
                        <td>${card.numeroTarjetaOperacion}</td>
                        <td>${card.fechaVigenciaTarjetaOperacion}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </jsp:body>
</base:basePage>