<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<c:if test="${!empty uSession}">
	<nav class="navbar navbar-default navbar-fixed-top">


		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<%-- 				<a class="navbar-brand" href="${contextPath}"><spring:message --%>
				<%-- 						code="header.administration" /></a> --%>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">

					<c:if test='${uSession.rol.equals("ROLE_EXTERNAL")}'>
						<li><a href="control-card-management"> <spring:message
								code="header.administration.control.card" /></a></li>
					</c:if>

					<c:if test='${uSession.rol.equals("ROLE_SDM")}'>
						<li><a href="search-control-card"> <spring:message
								code="header.administration.control.card.search" /></a></li>
					</c:if>
					<!-- drivers -->


					<%-- <c:if test=""> --%>
					<c:if test='${uSession.rol.equals("ROLE_SDM")}'>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"><spring:message
									code="header.administration.users" /><span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="consult-external-user"><span
										class="flag-icon flag-icon-gb" aria-hidden="true"></span>Consulta
										de Usuarios</a></li>
								<li><a href="create-external-user"><span
										class="flag-icon flag-icon-es" aria-hidden="true"></span>Creación
										de Usuarios</a></li>
								<li><a href="consult-manager-user"><span
										class="flag-icon flag-icon-es" aria-hidden="true"></span>Usuarios
										Administrador</a></li>
							</ul></li>
					</c:if>
					<!-- users -->

					<c:if test='${uSession.rol.equals("ROLE_SDM")}'>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> <spring:message
									code="header.administration.factorcalidad" /> <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<li><a href="cargar-factor-calidad"> <span
										class="flag-icon flag-icon-gb" aria-hidden="true"></span> <spring:message
											code="header.administration.factorcalidad.cargararchivo" />
								</a></li>
								<li><a href="modificar-factor-calidad"> <span
										class="flag-icon flag-icon-es" aria-hidden="true"></span> <spring:message
											code="header.administration.factorcalidad.modificar" />
								</a></li>
							</ul></li>
					</c:if>

					<c:if test='${uSession.rol.equals("ROLE_SDM")}'>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> <spring:message
									code="header.administration.reporte" /> <span class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<li><a href="report-refrender"> <span
										class="flag-icon flag-icon-gb" aria-hidden="true"></span> <spring:message
											code="header.administration.reporte.refrendacion" />
								</a></li>
								<li><a href="consultar-reportes"><span
										class="flag-icon flag-icon-es" aria-hidden="true"></span>Reporte
										SIRC</a></li>
							</ul>
						</li>
					</c:if>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test='${uSession.rol.equals("ROLE_EXTERNAL")}'>
						<li><a id="alerts" href="notifications"> <i
								class="fa fa-bell-o icon eq-ui-badge eq-ui-badge-overlap"
								data-badge="0"></i>
						</a></li>
					</c:if>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">${uSession.nombre}<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<c:if test='${uSession.rol.equals("ROLE_EXTERNAL")}'>
								<li><a href="change-password"><span
										class="flag-icon flag-icon-gb" aria-hidden="true"></span>Cambiar
										Contraseña</a></li>
							</c:if>
							<li><a href="javascript:formSubmit()"><span
									class="flag-icon flag-icon-es" aria-hidden="true"></span>Salir</a></li>
						</ul></li>


				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->


		<form action="logout" method="POST" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>


	</nav>
</c:if>