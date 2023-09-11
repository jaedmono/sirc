<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<base:basePage containerClass="home-container">

	<jsp:attribute name="footer">
			<script
			src="${pageContext.request.contextPath}/resources/js/dev/company.js"></script>
	</jsp:attribute>

	<jsp:body>
	
		<spring:message code="header.administration.users.consult" var="textTitle" />
		<spring:message code="form.user.email" var="labelEmail" />
		<spring:message code="common.button.text.consult" var="labelButton" />
		<spring:message code="common.button.text.add" var="addButton" />
		<spring:message code="table.col.tiitle.user" var="colTextUser" />
		<spring:message code="table.col.tiitle.options" var="colTextOptions" />
		<spring:message code="table.col.tiitle.options.associate" var="colTextOptAssociate" />
		<spring:message code="table.col.tiitle.options.delete" var="colTextOptDelete" />
		<spring:message code="common.button.text.accept" var="labelAccept" />
		<spring:message code="common.button.text.deny" var="labelDeny" />
		<spring:message code="table.col.tiitle.company" var="colTextCompany" />
		<spring:message code="common.table.col.tiitle.delete.register" var="colTextDeleteConfimration" />
		<spring:message code="header.administration.company.add" var="labelTextCompany" />
	
	<div class="jumbotron">
	    <div class="container">
	        <h2>${textTitle}</h2>
	    </div>
	
			<div class="row">
				<div class="col-xs-12">
					    <div class="row">
					    <div class="col-xs-12">
					           <div class="col-xs-12">
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
					            <th>${colTextOptAssociate}</th>
					            <th>${colTextOptDelete}</th>
					        </tr>
					        </thead>
					        <tbody>
						        <c:forEach items="${users}" var="user">
							        <tr>
							            <td class="eq-ui-data-table-cell-non-numeric">${user.loginUsuario}</td>
							            <td>
								            <span data-toggle="modal" data-target="#addCompany">
								            	<a onclick="getCompaniesByIdUser(${user.id})"  title="${colTextOptAssociate}"
												class="btn eq-ui-btn-fab eq-ui-btn-mini-fab eq-ui-waves-light fa-icon" ><i
													class="fa fa-building icon"></i></a>
											</span>
										</td>
										<td>
							            	<a id="${user.id}" href="#" title="${colTextOptDelete}"
											class=" removeRow btn eq-ui-btn-fab eq-ui-btn-mini-fab eq-ui-waves-light fa-icon"
											data-toggle="modal" data-target="#deleteUser"><i
												class="fa fa-trash icon fa-icon"></i></a>
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
	<div id="addCompany" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
		    	<div class="modal-header">
		      		<button type="button" class="close" data-dismiss="modal">&times;</button>
		        	<h4 class="mdl-dialog__title">${labelTextCompany}</h4>
		      	</div>
		      	<div class="modal-body" style="overflow-y: auto ">
		      	<div id="page-modal-container" class="col-xs-12">
					<jsp:include page="../common/page-message.jsp" />
              	</div>
				<!-- page message -->
		      	<div class="row">
			  		<div class="col-xs-12">
						<label for="company" data-error="Ingrese el nombre de la empresa y seleccionelo de la lista">${colTextCompany}</label>
					</div>
					<div class="col-xs-10">
						<div class="form-group eq-ui-input-field">
							<form class="form-validation">
								<input id="company" name="" data-parsley-trigger="change"
											data-parsley-minlength="1" type="text"
											data-provide="typeahead"
											class="form-control eq-ui-input typeahead" value="" required>
							</form>
				    	</div>
					</div>
			    	<div class="col-xs-2">
			        	<div class="form-group eq-ui-input-field text-center">
				        <a class="btn btn-info eq-ui-waves-light button-validation"
							onclick="validateAddCompany()">${addButton}</a>
				    	</div>
				 	</div>
			  	</div>
		        
		        <div class="row">
					<div class="col-md-12">
						<!--  Table -->
						<div class="eq-ui-card eq-ui-card-with-table table table-striped table-bordered">
						    <table id="tableY" class="table eq-ui-data-table ">
						        <thead>
						        <tr>
						            <th class="eq-ui-data-table-cell-non-numeric">${colTextCompany}</th>
						            <th>${colTextOptions}</th>
						        </tr>
						        </thead>
						        <tbody>
						        </tbody>
						    </table>
						</div>
					</div>
				</div>
		      </div>
		    </div>
		  </div>
		</div>
		
<!-- 		confirmar eliminar usuario -->

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
							class="btn btn-success eq-ui-waves-light button-validation"
							data-dismiss="modal" id="deleteExtUser" data-row="">${labelAccept}</button>
				      	 <button type="button"
							class="btn btn-danger eq-ui-waves-light" data-dismiss="modal">${labelDeny}</button> 
			      </div>
			    </div>
				
			</div>
		</div>
		
		<!-- 		confirmar desasociar empresa       -->
		
		<div id="disasociateCompany" class="modal fade" role="dialog">
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
							class="btn btn-success eq-ui-waves-light button-validation"
							data-dismiss="modal" id="removeCompanyToUser" data-row="">${labelAccept}</button>
				      	 <button type="button" id="cancelRemoveCompanyToUser"
							class="btn btn-danger eq-ui-waves-light" data-dismiss="modal">${labelDeny}</button> 
			      </div>
			    </div>
				
			</div>
		</div>
		
	</jsp:body>

</base:basePage>