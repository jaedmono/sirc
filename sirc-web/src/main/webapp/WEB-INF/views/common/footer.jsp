<%@ page contentType="text/html;charset=UTF-8"%>

<%-- <script src="${pageContext.request.contextPath}/resources/libraries/jquery.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/libraries/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/libraries/moment-with-locales.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/libraries/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
 --%>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/libraries/mdl/js/exentriq-bootstrap-material-ui.min.js?v=0.3.2"></script>

<script
	src="${pageContext.request.contextPath}/resources/libraries/util/zen/js/Util.js"></script>

<!-- <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
 -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>
 -->
<!-- Developer -->

<script
	src="${pageContext.request.contextPath}/resources/js/common/modal.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/common/validation-form.js"></script>


<script
	src="${pageContext.request.contextPath}/resources/libraries/datatables/1.10.15/js/jquery.dataTables.min.js"></script>

<script
	src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/libraries/typeahead/js/typeahead.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/libraries/moment-with-locales.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/libraries/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
	
	<c:if test="${uSession == null}">
	<script type="text/javascript">
		$.get(
						"count-notifications",
						function(data) {
							$("#alerts")
									.html(
											'<i class="fa fa-bell-o icon eq-ui-badge eq-ui-badge-overlap" data-badge="'
					+ data + '"></i>');
						});
		<!--
	//-->
	</script>
</c:if>

<div class="footer">
	<div class="footer-bg2" style="font-size: 18px; margin-top: 100px;">
		<div class="container" id="footer" style="padding: 0px; ">
			<div class="container medio" >
				<div class="row " >
					<div class="col-md-6 col-sm-12 col-xs-12 text-center footer-left"
						style="font-size: 18px; padding-left: 45px;">
						<div class="text-left">Secretaría Distrital de Movilidad</div>
						<div class="text-left">Calle 13 No.37 - 35</div>
						<div class="text-left">Horario de Atención al Público Lunes a viernes de 7:00am a 4:30pm</div>
						<div class="text-left">E-mail de Servicio al Ciudadano: servicioalciudadano@movilidadbogota.gov.co</div>
						<div class="text-left">Línea del Conmutador: 3649400 - 3649416</div>
					</div>
					<div class="col-md-6 col-sm-12 col-xs-12 footer_direccion"
						style="font-size: 18px;">
						<div class="one-three">
							<ul class="faico">
								<li><a href="#"><i class="fa fa-phone" title="195"></i><span style="color: #4b4b4b;">195</span></a></li>	
								<li><a href="#"><i class="fa fa-facebook-official fa2x" title="Ingresar a Facebook"></i></a></li>					
								<li><a href="#"><i class="fa fa-twitter-square fa2x" title="Ingresar a Twitter"></i></a></li>
								<li><a href="#"><i class="fa fa-youtube-square fa2x" title="Ingresar a YouTube"></i></a></li>
								<li><a href="#"><i class="fa fa-instagram fa2x" title="Ingresar a Instagram"></i></a></li>
							</ul>	
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer-bg3 text-center footer-copy"
		style="font-size: 1.2em; font-stretch: condensed;">© 2017 Secretaría Distrital de Movilidad</div>
</div>