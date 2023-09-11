<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<base:basePage containerClass="home-container">

	<jsp:body>
		
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
		
		
		<div class="row">
			<div class="col-md-3 col-md-offset-5 centercard">
                 <h3 class="header">
					<spring:message code="header.administration.login" />
				</h3>
                  <div class="eq-ui-card doc-margin-bottom centercard2">
                        <div class="eq-ui-card-supporting-text">
                            <form id="formLoggin" lang="es"
							action="<c:url value='j_spring_security_check' />"
							class="form-validation" method="POST">
							  <div class="form-group eq-ui-input-field">
							  	<label for="username">${labelUsername}</label>
							    <input class="form-control eq-ui-input" type="text"
									id="username" name="username" value="" required>
							    
							  </div>
							  <div class="form-group eq-ui-input-field">
							  	<label for="password">${labelPass}</label>
								<input class="form-control eq-ui-input" type="password"
									id="password" name="password" value="" required>
								
							  </div>
							  <input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							</form>
                        </div>
                        <br>
                        <div class="row">
						    <div class="col-md-2 col-md-offset-5" style="width: 400px;">
						    	<a id="show-dialog" class="pass-forget" data-toggle="modal"
								data-target="#forgotModal">Recordar mi contraseña</a>
						    </div>
						</div>
						<br>
                        <button type="submit"
						class="btn btn-info eq-ui-waves-light button-validation col-md-12"
						form="formLoggin">${textButtonLogin}</button>
                    </div>
                    
                </div>
		</div>
		
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
		    		<form action="send-new-password" class="form-validation" id="ask_new_password" method="POST">
		    		<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
				      <div class="form-group eq-ui-input-field">
				       <label for="email">${labelEmail}</label>
					    <input class="form-control eq-ui-input" type="text" id="email" name="email"
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
		      	<button form="ask_new_password" type="submit"
							class="btn btn-info eq-ui-waves-light button-validation">${labelAccept}</button>
		      	<button type="button" class="btn btn-info eq-ui-waves-light"
							data-dismiss="modal">${labelDeny}</button>
		      </div>
		    </div>
		
		  </div>
		</div>
	</jsp:body>

</base:basePage>