<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<base:basePage containerClass="home-container">

	<jsp:attribute name="footer">
			<script>
				$(window).load(function() {
					$('#alertModal').modal('show');
				});
			</script>
	</jsp:attribute>

	<jsp:body>
		<%-- <div class="col-xs-12">
			<div class="page-header">
				<h1>
					<spring:message code="header.administration" />
				</h1>
			</div>
		</div>
		<!-- header -->

		<div class="col-xs-12">
			<p>
				<spring:message code="home.description" />
			</p>
		</div> --%>
		
		<spring:message code="form.user.type" var="labelUserType" />
		<spring:message code="form.user.name" var="labelUsername" />
		<spring:message code="form.user.pass" var="labelPass" />
		<spring:message code="header.administration.login"
			var="textButtonLogin" />
		<spring:message code="form.user.pass.title" var="titlePass" />
		<spring:message code="form.user.email" var="labelEmail" />
		<spring:message code="form.user.pass.text1" var="text1Pass" />
		<spring:message code="form.user.pass.text2" var="text2Pass" />
		<spring:message code="form.user.pass.forgoten" var="linkForgotenPass" />
		<spring:message code="common.button.text.accept" var="labelAccept" />
		<spring:message code="common.button.text.deny" var="labelDeny" />
		<spring:message code="header.administration.notifications"
			var="titleModal" />		
		<!-- Modal -->
		<div id="forgotModal" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		      	<button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="mdl-dialog__title">${titlePass}</h4>
		      </div>
		      <div class="modal-body">
		        <div class="">
		    		<form action="#" class="form-validation">
				      <div class="form-group eq-ui-input-field">
				      <label for="email">${labelEmail}</label>
					    <input class="form-control eq-ui-input" type="text" id="email"
										required>
					    
					  </div>
					</form>
			      <p>
			        ${text1Pass}
			      </p>
			      <p>
			        ${text2Pass}
			      </p>
		        </div>
		      </div>
		      <div class="modal-footer">
		      	<button type="button"
							class="btn btn-info eq-ui-waves-light button-validation">${labelAccept}</button>
		      	<button type="button" class="btn btn-info eq-ui-waves-light"
							data-dismiss="modal">${labelDeny}</button>
		      </div>
		    </div>
		
		  </div>
		</div>
		
		<!-- Modal -->
		<c:if test="${!empty alert}">
			<div id="alertModal" class="modal fade" role="dialog">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			      	<button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="mdl-dialog__title">${titleModal}</h4>
			      </div>
			      <div class="modal-body">
			        <div class="">
			        <form action="notifications" id="goToAlerts" method="get"></form>
			        ${alert}
			        </div>
			      </div>
			      <div class="modal-footer">
			      	<button type="submit"
								class="btn btn-success eq-ui-waves-light button-validation" form="goToAlerts" formmethod="get">${labelAccept}</button>
			      	<button type="button" class="btn btn-danger eq-ui-waves-light"
								data-dismiss="modal">${labelDeny}</button>
			      </div>
			    </div>
			
			  </div>
			</div>
		</c:if>
		
	</jsp:body>

</base:basePage>