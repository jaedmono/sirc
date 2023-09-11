<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<base:basePage containerClass="home-container">

	<jsp:attribute name="footer">
			<script
			src="${pageContext.request.contextPath}/resources/js/dev/notifications/notifications.js"></script>
	</jsp:attribute>

	<jsp:body>
	
		<spring:message code="header.administration.notifications.validez"
			var="textTitle" />
			
		<div class="jumbotron">
	    <div class="container">
	        <h2>${textTitle}</h2>
	    </div>
	
			<div class="row">
				<div class="col-md-12">
					
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<!--  Table -->
					<div
						class="eq-ui-card eq-ui-card-with-table table table-striped table-bordered">
					    <table id="notifications1"
							class="table eq-ui-data-table eq-ui-centered z-depth-1">
					        <thead>
					        <tr>
					            <th class="eq-ui-data-table-cell-non-numeric">Tarjeta de control</th>
					            <th>Número de documento</th>
					            <th>Nombres</th>
					            <th>Placa</th>
					            <th>Fecha fin validez</th>
					            <th>Días sin validez</th>
					        </tr>
					        </thead>
					        <tbody>
						        <c:forEach items="${tarjetasControlSinValidez}"
									var="tarjeta">
							        <tr>
							            <td>${tarjeta.tarjetaControl}</td>
							            <td>${tarjeta.numeroDocumento}</td>
							            <td>${tarjeta.nombres}</td>
							            <td>${tarjeta.placa}</td>
							            <td>${tarjeta.fecha}</td>
							            <td>${tarjeta.dias}</td>
							        </tr>
						        </c:forEach>
					        </tbody>
					    </table>
					</div>
				</div>
			</div>
	</div>
	</jsp:body>

</base:basePage>