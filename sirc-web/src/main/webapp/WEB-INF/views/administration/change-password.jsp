<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<base:basePage containerClass="home-container">
	
	<jsp:attribute name="footer">
			<script
			src="${pageContext.request.contextPath}/resources/js/dev/administracion/change-password.js"></script>
	</jsp:attribute>
	
	<jsp:body>
		
		<spring:message code="form.pass.change" var="labelChangePass" />
		<spring:message code="form.user.pass" var="labelPass" />
		<spring:message code="form.pass.confirm" var="labelConfirmPass" />
		<spring:message code="form.pass.update" var="labelUpdatePass" />
		

		<div class="col-xs-12">
		</div>
		
		<div class="row">
				<div class="col-md-3 col-md-offset-5 centercard">
                 <h3 class="header">${labelChangePass}</h3>
                  <div class="eq-ui-card doc-margin-bottom centercard2">
                        <div class="eq-ui-card-supporting-text">
                            <form action="change-password"
							class="form-validation" method="POST" id="formChangePassword">
							  <div class="form-group eq-ui-input-field">
								<input id="password" name="nuevaClave"
									class="form-control eq-ui-input" type="password" required>
								<label for="password" data-error="Obligatorio">${labelPass}</label>
							  </div>
							  <div class="form-group eq-ui-input-field">
								<input id="password2" class="form-control eq-ui-input"
									data-error="Required" type="password" required>
								<label for="password2" data-error="Obligatorio">${labelConfirmPass}</label>
							  </div>
							  
							  <input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							</form>
                        </div>
                        <button type="submit"
						class="btn btn-info eq-ui-waves-light button-validation"
						id="cPassButton">${labelUpdatePass}</button>
                    </div>
                </div>		
		</div>
	</jsp:body>

</base:basePage>