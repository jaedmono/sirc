<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<%@tag description="Base page tag" pageEncoding="UTF-8"%>

<%--Variables--%>
<%@attribute name="containerClass" required="true"%>

<%--Fragments--%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="variables" fragment="true"%>
<%@attribute name="footer" fragment="true"%>

<html lang="es">

<head>
	<jsp:include page="../common/head.jsp"/>
	<jsp:invoke fragment="head"/>
</head>

<body>

<jsp:include page="../common/nav.jsp"/>

<%-- Global variables --%>
<%-- TODO: Add locale dependent javascript error message --%>
<input id="variables" type="hidden" data-locale="${pageContext.response.locale}"
	   data-server-timezone-offset="${constants.administration.zoneOffset}">

<jsp:invoke fragment="variables"/>

<div class="container ${containerClass}">
	<div class="row white-background">
	
		<div class="col-xs-12">
			<jsp:include page="../common/breadcrumb.jsp"/>
		</div><!-- breadcrumb -->
		
		<div class="col-xs-12">
			<jsp:include page="../common/menu.jsp"/>
		</div>

		<div id="page-message-container" class="col-xs-12">
			<jsp:include page="../common/page-message.jsp"/>
		</div><!-- page message -->

		<jsp:doBody/>

	</div>
</div>

<jsp:include page="../common/footer.jsp"/>

<jsp:invoke fragment="footer"/>

</body>
</html>