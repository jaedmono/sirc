<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<base:basePage containerClass="home-container">

	<jsp:attribute name="footer">
			<script
			src="${pageContext.request.contextPath}/resources/js/dev/manager.js"></script>
	</jsp:attribute>

	<jsp:body>
	
		<spring:message code="header.administration.manager.users.consult"
			var="textTitle" />
		<spring:message code="table.col.tiitle.user" var="labelUsername" />
		<spring:message code="common.button.text.consult" var="labelButton" />
		<spring:message code="common.button.text.search" var="searchButton" />
		<spring:message code="common.button.text.add" var="addButton" />
		<spring:message code="table.col.tiitle.user" var="colTextUser" />
		<spring:message code="table.col.tiitle.options" var="colTextOptions" />
		<spring:message code="table.col.tiitle.options.delete" var="colTextOptDelete" />
		<spring:message code="common.button.text.accept" var="labelAccept" />
		<spring:message code="common.button.text.close" var="labelDeny" />
		<spring:message code="table.col.tiitle.user" var="colTextLabel" />
		<spring:message code="header.administration.user.add.ldap"
			var="labelTextModal" />
		<spring:message code="common.table.col.tiitle.delete.register"
			var="colTextDeleteConfimration" />
	
	<div class="jumbotron">
	    <div class="container">
	        <h2>${textTitle}</h2>
	    </div>
	    
	    	<div class="eq-ui-btn-fab-action">
            	<a data-toggle="modal" data-target="#addLdapUser"
					class="btn btn-info eq-ui-btn-fab eq-ui-waves-light waves-effect waves-effect waves-light"><i
					class="fa fa-plus-circle icon"></i> Crear Usuario Administrador</a>
            </div>
		        
			<div class="row">
				<div class="col-md-12">
					<form class="form-validation">
					    <div class="row">
					    	<br>
					           <div class="col-md-12">
					                <div class="form-group eq-ui-input-field">
					                  <label for="username"
										data-error="${labelUsername} Invalido">${labelUsername}</label>
					                    <input id="username" name="username"
										data-parsley-trigger="change" data-parsley-maxlength="50"
										type="text" class="form-control eq-ui-input" value="" required>
					                  
					                </div>
					        </div>
					    </div>
					    <div class="row">
					        <div class="col-xs-12">
					            <div
									class="form-group eq-ui-input-field text-center">
									<%--<a class="btn btn-info eq-ui-waves-light button-validation">${labelButton}</a> --%>
					                <a
										class="btn btn-info eq-ui-waves-light button-clean">Limpiar Búsqueda</a>
					            </div>
					        </div>
					    </div>
					</form>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<!--  Table -->
					<div
						class="eq-ui-card eq-ui-card-with-table table table-striped table-bordered">
					    <table id="tableX" class="table eq-ui-data-table ">
					        <thead>
					        <tr>
					            <th class="eq-ui-data-table-cell-non-numeric">${colTextUser}</th>
					            <th>${colTextOptDelete}</th>
					        </tr>
					        </thead>
					        <tbody>
						        <c:forEach items="${users}" var="user">
							        <tr>
							            <td class="eq-ui-data-table-cell-non-numeric">${user.username}</td>
							            <td>
							            	<a id="${user.id}" data-toggle="modal"
											data-target="#deleteUser" title="${colTextOptDelete}"
											class="removeManagerRow btn eq-ui-btn-fab eq-ui-btn-mini-fab eq-ui-waves-light fa-icon"><i
												class="fa fa-trash icon"></i></a>
							            </td>
							        </tr>
						        </c:forEach>
					        </tbody>
					    </table>
					</div>
				</div>
			</div>
	</div>
	
	<!-- Modal -->
	<div id="addLdapUser" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		      	<button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="mdl-dialog__title">${labelTextModal}</h4>
		      </div>
		      
			<div class="modal-body">
		      	<div id="page-modal-container" class="col-xs-12">
					<jsp:include page="../common/page-message.jsp" />
              	</div>
						
			
				<div class="row">
			  			<div class="col-md-12">
			  				<label for="usernameldap" 
			  					data-error="Ingrese el nombre del usuario y seleccionelo de la lista">${colTextLabel}
			  				</label>
			  			</div>
			  			<div class="col-md-10">
			  				<div class="form-group eq-ui-input-field">
								<form class="form-validation">
									<input id="usernameldap" name="" 
										data-parsley-minlength="1" type="text"
										class="form-control eq-ui-input" value="" required>
					        
								</form>
				    		</div>
			  			</div>
						<div class="col-md-2">
			        		<div class="form-group eq-ui-input-field text-center">
				          		<a id="searchButton" class="btn btn-info eq-ui-waves-light button-validation">${searchButton}</a>
				   			</div>
				 		</div>
			  </div>
			</div>
			<div class="modal-footer">
		    	<button type="button" disabled id="addLdapUserButton"
					class="btn btn-info eq-ui-waves-light button-validation"
					data-dismiss="modal">${addButton}</button>
		        <button type="button" class="btn btn-info eq-ui-waves-light"
					data-dismiss="modal">${labelDeny}</button> 
			</div>
		    </div>
		
		  </div>
		</div>
		
		<!-- 	Modal Confirmación Eliminación -->
		<div id="deleteUser" class="modal fade" role="dialog">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				      	<button type="button" class="close" data-dismiss="modal">&times;</button>
				        <h4 class="mdl-dialog__title">${colTextDeleteConfimration}</h4>
				      </div>
				      <div class="modal-body">
				      <div id="page-modal-container" class="col-xs-12">
						<jsp:include page="../common/page-message.jsp" />
		              </div>
								<!-- page message -->
				      <div class="row">
					  	<div class="col-xs-8">
							<div class="form-group eq-ui-input-field">
								<spring:message code="common.table.col.tiitle.msg.register" />
						    </div>
						</div>
					  </div>
				      </div>
				      <div class="modal-footer">
				      	<button type="button"
							class="btn btn-success eq-ui-waves-light button-validation" title="${colTextOptDelete}"
							data-dismiss="modal" id="deleteExtUser" data-row="">${labelAccept}</button>
				      	 <button type="button"
							class="btn btn-danger eq-ui-waves-light" data-dismiss="modal">${labelDeny}</button> 
			      </div>
			    </div>
				
			</div>
		</div>
		
	</jsp:body>

</base:basePage>