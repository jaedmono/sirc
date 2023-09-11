<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="container banner">
	<div class="row head-banner">
		<div class="col-md-2">
			<img class="logo-bogota img-responsive img-center " style="max-width: 100%;"
				src="${pageContext.request.contextPath}/resources/images/sirc/logo-movilidad.png"
				alt="Logo Bogotá Mejor para todos." />
		</div>
		<div class="col-md-10 right-header">
<!-- 		</div> -->
<!-- 		<div class="col-md-2"> -->
			<img class="logo-bogota img-responsive img-center img-logo" style="max-width: 100%;"
				src="${pageContext.request.contextPath}/resources/images/sirc/logo-simur.png"
				alt="Logo Bogotá Mejor para todos." />
			<div class="title bold negro ">SIRC</div>
			<div class="subTitle negro">SISTEMA DE INFORMACIÓN Y REGISTRO DE CONDUCTORES</div>
		</div>
	</div>
</div>

<div class="banner-sirc">
	<img class="icon-img" src="${pageContext.request.contextPath}/resources/images/sirc/icon-sirc.png" alt="Taxi"></img>
</div>

<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>