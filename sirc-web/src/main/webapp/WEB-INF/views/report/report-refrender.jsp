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
		<script>
			$('#datepickerInicio').datetimepicker({
				locale : 'es',
				format : 'YYYY/MM/DD'
			});
			$('#datepickerFinal').datetimepicker({
				locale : 'es',
				format : 'YYYY/MM/DD',
				useCurrent : false
			});
			$("#datepickerInicio").on("dp.change", function(e) {
				$('#datepickerFinal').data("DateTimePicker").minDate(e.date);
			});
			$("#datepickerFinal").on("dp.change", function(e) {
				$('#datepickerInicio').data("DateTimePicker").maxDate(e.date);
			});
			var dataTable = $('#tableX').dataTable({});
			$("#email").keyup(function() {
				dataTable.fnFilter(this.value);
			});
		</script>
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
    </jsp:attribute>

	<jsp:body>
	<spring:message code="header.administration.reporte.refrendacion"
			var="textTitle" />
	
        <div class="container">
                <h2>${textTitle}</h2>
                 <p>Elija el rango de fechas de expedición de la tarjeta de control</p>
            <form:form id="formReportRefrender"
				action="search-report-refrender" class="form-util" method="GET">
            	<div class="row">
	                <div class="col-md-6">
	                	<div class="form-group">
		                	 <label for="datepickerInicio">Fecha inicio</label>
		                	 <div class="input-group date">
								  <input type="text" class="form-control"
									name="datepickerInicio" id="datepickerInicio"
									value="${datepickerInicio}"><span
									class="input-group-addon"><i
									class="glyphicon glyphicon-th"></i></span>
							 </div>
						 </div>
	                </div>
	                <div class="col-md-6">
	                	<div class="form-group">
		                	<label for="datepickerFinal">Fecha final</label>
		                	 <div class="input-group date">
								  <input type="text" class="form-control" name="datepickerFinal"
									id="datepickerFinal" value="${datepickerFinal}"><span
									class="input-group-addon"><i
									class="glyphicon glyphicon-th"></i></span>
							 </div>
						 </div>
	                </div>
	             </div>
	             <div class="row">
	                <div class="col-md-6">
	                	 <div class="form-group">
						    <button type="submit" id="btn-report-refreder"
								class="btn btn-info eq-ui-waves-light button-validation">Buscar</button>
						  </div>
	                </div>
	                
	                <div class="col-md-3">
	                	 <div class="form-group">
						    <a href="search-report-refrender-donwload-all?type=csv"
								class="btn btn-info" role="button">Descargar Reporte Completo CSV</a>
						  </div>
	                </div>
	                
	                <div class="col-md-3">
	                	 <div class="form-group">
						    <a href="search-report-refrender-donwload-all?type=excel"
								class="btn btn-info" role="button">Descargar Reporte Completo EXCEL</a>
						  </div>
	                </div>
	                
	             </div>
	             
             </form:form>                       
		</div>
		<div class="container">
			
			<c:choose>
				<c:when test="${not empty sumaryRows && sumaryRows < 50}">
					<div class="row">
						<div class="col-md-12">
							<!--  Table -->
							<div
								class="eq-ui-card eq-ui-card-with-table table table-striped table-bordered">
							    <table id="tableX" class="table eq-ui-data-table ">
							        <thead>
							        <tr>
							            <th class="eq-ui-data-table-cell-non-numeric">Tarjeta de control</th>					            
							            <th>Placa vehiculo</th>
							            <th>Razon social</th>
							            <th>Fecha de Vigencia</th>
							            <th>Estado</th>
							            <th>Detalle</th>
							        </tr>
							        </thead>
							        <tbody>
								        <c:forEach items="${reportRefrenderDto}" var="user">
									        <tr>
									            <td class="eq-ui-data-table-cell-non-numeric">${user.tarjetaControl}</td>
									            <td class="eq-ui-data-table-cell-non-numeric">${user.placaSerialVehiculo}</td>
									            <td class="eq-ui-data-table-cell-non-numeric">${user.empresa.nombreRazonSocial}</td>
									            <td class="eq-ui-data-table-cell-non-numeric">${user.fechaVigencia}</td>
									            <td class="eq-ui-data-table-cell-non-numeric">${user.idEstado.descripcion}</td>
									            <td> 
									            	<button type="button" class="btn btn-default"
														aria-label="Left Align" data-toggle="modal"
														data-target="#Modal${user.tarjetaControl}">
														<span class="fa fa-external-link fa-lg" aria-hidden="true"></span>
													</button>
												</td>
									        </tr>
								        </c:forEach>
							        </tbody>
							    </table>
							</div>
							
							<c:forEach items="${reportRefrenderDto}" var="user">
						        <div class="modal" id="Modal${user.tarjetaControl}"
									tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
								  <div class="modal-dialog" role="document">
								    <div class="modal-content">
								      <div class="modal-header">
								        <button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								        <h3 class="modal-title" id="myModalLabel">Tarjeta de operacion ${user.tarjetaControl}</h3>
								      </div>
								      <div class="modal-body">
								        <table id="tableY" class="table eq-ui-data-table ">
									        <thead>
									        <tr>
									            <th class="eq-ui-data-table-cell-non-numeric">ID</th>					            
									            <th>Fecha Refrendacion</th>
									        </tr>
									        </thead>
									        <tbody>
										        <c:forEach items="${user.refrendacionHistorico}"
															var="refrenacion">
											        <tr>
											            <td class="eq-ui-data-table-cell-non-numeric">${refrenacion.idRefrendacionHistorico}</td>
											            <td class="eq-ui-data-table-cell-non-numeric">${refrenacion.fechaRefrendacion}</td>
											        </tr>
										        </c:forEach>
									        </tbody>
									    </table>
								      </div>
								    </div>
								  </div>
								</div>
					        </c:forEach>
					        
						</div>
					</div>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${not empty sumaryRows}">
		            <div class="row">
		           		<div class="col-md-10"></div>
		                <div class="col-md-1">
		                	<div class="form-group">
		                		<a
									href="search-report-refrender-donwload?datepickerInicio=${datepickerInicio}&datepickerFinal=${datepickerFinal}&type=csv"
									class="btn btn-info" role="button">CSV</a>
		                	</div>
		               	</div>
		               	
		               	<div class="col-md-1">
		                	<div class="form-group">
		                		<a
									href="search-report-refrender-donwload?datepickerInicio=${datepickerInicio}&datepickerFinal=${datepickerFinal}&type=excel"
									class="btn btn-info" role="button">XLS</a>
		                	</div>
		               	</div>
		               	
		               	<!-- boton de descargar excel -->
		       		</div>
				</c:when>
			</c:choose>
		</div>
    </jsp:body>
</base:basePage>