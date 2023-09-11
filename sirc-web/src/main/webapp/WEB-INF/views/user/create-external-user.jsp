<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<base:basePage containerClass="home-container">

	<jsp:body>
	
		<spring:message code="header.administration.users.create"
			var="textTitle" />
		<spring:message code="form.user.email" var="labelEmail" />
		<spring:message code="form.user.create.button" var="labelButton" />
	
	<div class="jumbotron">
	    <div class="container">
	        <h2>${textTitle}</h2>
	    </div>
	
			<div class="row">
			<div class="col-xs-12">
				<div class="col-xs-12">
					<form action="create-external-user" commandName="userDTO"
						class="form-util" method="POST">
					<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					    <div class="row">
					           <div class="col-md-12">
					                <div class="form-group eq-ui-input-field">
					                	<label for="email"
										data-error="${labelEmail} Invalido">${labelEmail}</label>
					                    <input id="email" name="email"
										data-parsley-trigger="change" data-parsley-maxlength="100"
										type="email" class="form-control eq-ui-input"
										value="${userDTO.email}" required>
					                    
					                </div>
					        </div>
					    </div>
					    <div class="row">
					        <div class="col-md-12">
					            <div
									class="form-group eq-ui-input-field col-md-2 col-md-offset-5">
					                <a
										class="btn btn-info eq-ui-waves-light button-validation">${labelButton}</a>
					            </div>
					        </div>
					    </div>
					</form>
				</div>
			</div>
			</div>
	</div>
	</jsp:body>

</base:basePage>