<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<base:basePage containerClass="home-container">

	<jsp:attribute name="footer">
			<script
			src="${pageContext.request.contextPath}/resources/js/dev/report.js"></script>
			<meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
	</jsp:attribute>

	<jsp:body>
	
	<div class="jumbotron">
	    <div class="container">
	        <h2>${textTitle}</h2>
	        <p>La consulta se realiza para las fechas de expedición de la tarjeta de control, 
	        	validez de la tarjeta de operación, vigencia de la tarjeta de control, vencimiento del SOAT,
	        	vencimiento de la revisión técnico-mecánica y vencimiento de la tarjeta de operación.  
	        	Elija un rango de fechas o la opción de descarga del reporte completo.
	        </p>
	    </div>
		        
			<div class="row">
				<div class="col-md-12">
					<form id="form-reporte" class="form-validation" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					    <div class="row">
					    	<br>
					           <div class="col-md-6">
					                <div class="form-group">
						                <label for="fechaInicial" data-error="Fecha Inicial Invalida">Fecha Inicial: </label>
						                <div class='input-group date' id="fecha-inicial">
						                    <input type='text' name="fechaInicial" class="form-control" value="<fmt:formatDate value="${initialDate}" pattern="dd/MM/YYYY"/>">
						                    <span class="input-group-addon">
						                        <span class="glyphicon glyphicon-calendar"></span>
						                    </span>
						                </div>
<!-- 					                  <label for="fechaInicial" -->
<!-- 										data-error="Fecha Inicial Invalido">Fecha Inicial: </label> -->
<!-- 						                    <input id="fechaInicial" name="fechaInicial" -->
<!-- 											placeholder="Fecha Inicial (dd/mm/aaaa)" required -->
<!-- 											type="text" class="form-control eq-ui-input datepicker fechaInicial"  -->
<%-- 											value="<fmt:formatDate value="${initialDate}" pattern="dd/MM/YYYY"/>"> --%>
<!-- 											<span class="input-group-addon"> -->
<!-- 						                        <span class="glyphicon glyphicon-calendar"></span> -->
<!-- 						                    </span> -->
					                  	<input type="hidden" id ="txt-tipo-reporte" name="tipoReporte">
					                </div>
					        </div>
					        <div class="col-md-6">
					                <div class="form-group">
					                	<label for="fechaFinal" data-error="Fecha Final Invalida">Fecha Final: </label>
						                <div class='input-group date' id="fecha-final">
						                    <input type='text' name="fechaFinal" class="form-control" value="<fmt:formatDate value="${finalDate}" pattern="dd/MM/YYYY"/>">
						                    <span class="input-group-addon">
						                        <span class="glyphicon glyphicon-calendar"></span>
						                    </span>
						                </div>
<!-- 					                  <label for="fechaFinal" -->
<!-- 										data-error="Fecha Final Invalido">Fecha Final: </label> -->
<!-- 						                    <input id="fechaFinal" name="fechaFinal" -->
<!-- 											placeholder="Fecha Final (dd/mm/aaaa)" required -->
<!-- 											type="text" class="form-control eq-ui-input datepicker"  -->
<%-- 											value="<fmt:formatDate value="${finalDate}" pattern="dd/MM/YYYY"/>"> --%>
					                </div>
					        </div>
					        <br>
					        <div class="col-md-6">
					                <div class="radio">
                                      <label><input type="radio" name="opcionFecha" value="porFecha" checked>Descargar reporte por fechas</label>&nbsp;&nbsp;
                                      <label><input type="radio" name="opcionFecha" value="completo">Descargar reporte completo</label>
                                   </div>
                                   <div class="radio">
                                      
                                   </div>
					        </div>
					        <div class="col-md-6">
					                
					        </div>
					    </div>
					    <br>
					    <div class="row">
					        <div class="col-xs-12">
					            <div class="form-group eq-ui-input-field text-center">
<!-- 									<button class="btn btn-info eq-ui-waves-light " type="submit">Obtener Reporte</button> -->
									<button class="btn btn-info eq-ui-waves-light" id="btn-reporte-csv" type="button">Descargar Reporte CSV</button>
									<button class="btn btn-info eq-ui-waves-light" id="btn-reporte-excel" type="button">Descargar Reporte EXCEL</button>
<!-- 					                <a class="btn btn-info eq-ui-waves-light button-clean">Limpiar Búsqueda</a> -->
					            </div>
					        </div>
					    </div>
					</form>
				</div>
			</div>
			
		
	</div>
	
	</jsp:body>

</base:basePage>