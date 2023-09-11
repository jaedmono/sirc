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
	
		<spring:message code="header.administration.notifications"
			var="textTitle" />
			
		<div class="jumbotron">
	    <div class="container">
	        <h2>${textTitle}</h2>
	    </div>
	
		<div class="col-md-12">
			<div class="row" style="margin-bottom: 100px;">
				<div class="col-md-4">
				<a href="notifications-sin-validez" class="eq-ui-badge text-white" data-badge="${tarjetasControlSinValidez}">
					<div class="eq-ui-card eq-ui-card-image img1-card-image-1 eq-ui-waves">
<!-- 					    <div class="eq-ui-card-title eq-ui-card-title-expand"></div> -->
<!-- 					    <div class="eq-ui-card-actions sin-validez"> -->
<!-- 					        <span class="shades-text-white">Tarjetas sin Validez</span> -->
<!-- 					    </div> -->
					</div>
					</a>
				</div>
				<div class="col-md-4">
					<!--  Table -->
					<a href="notifications-sin-vigencia" class="eq-ui-badge text-white" data-badge="${tarjetasControlSinVigencia}">
					<div class="eq-ui-card eq-ui-card-image img1-card-image-2 eq-ui-waves">
<!-- 					    <div class="eq-ui-card-title eq-ui-card-title-expand"></div> -->
<!-- 					    <div class="eq-ui-card-actions sin-vigencia"> -->
<!-- 					        <span class="shades-text-white">Tarjetas sin Vigencia</span> -->
<!-- 					    </div> -->
					</div>
					</a>
				</div>
				
				<div class="col-md-4">
					<a href="notifications-proximos-vencimientos" class="eq-ui-badge text-white" data-badge="${tarjetasControlProximosVencimientos}">
						<div class="eq-ui-card eq-ui-card-image img1-card-image-3 eq-ui-waves">
<!-- 						    <div class="eq-ui-card-title eq-ui-card-title-expand"></div> -->
<!-- 						    <div class="eq-ui-card-actions proximo-vencimiento"> -->
<!-- 						        <span class="shades-text-white">Tarjetas Próximas a Vencerse</span> -->
<!-- 						    </div> -->
						</div>
					</a>
				</div>
				
			</div>
		</div>
	</div>
	
				
	</jsp:body>

</base:basePage>