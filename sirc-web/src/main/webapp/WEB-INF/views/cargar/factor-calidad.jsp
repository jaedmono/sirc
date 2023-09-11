<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<base:basePage containerClass="home-container">
    <jsp:attribute name="footer">
        <script src="${pageContext.request.contextPath}/resources/js/dev/cargar/factor-calidad.js"></script>
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
    </jsp:attribute>
    <jsp:body>
        <spring:message code="header.administration.factorcalidad.cargararchivo" var="textTitle" />
        <div class="jumbotron">
            <div class="container">
                <h2>${textTitle}</h2>
            </div>
            <form:form id="formCargarArchivo" class="form-validation" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="row" style="margin-top: 15px;">
                    <div id="divArchivo" class="col-md-6">
                        <div class="col-md-6" style="margin-right: -12px;">
                            <div class="icon-addon addon-md">
                                <input id="nombreArchivo" type="text" class="form-control" placeholder="Archivo factor calidad" readonly required>
                                <label class="fa fa-file-text-o"></label>	
                            </div>
                        </div>
                        <div id="btnUpload" class="col-md-6" style="padding: 0px;">
                            <label class="input-group-btn">
                                <span id="btn-examinar" class="btn btn-primary">Seleccionar archivo
                                    <input id="factorqa-upload" type="file" style="display: none;" accept="text/plain">
                                </span>
                            </label>
                        </div>
                        <div id="btnsCargue" class="col-md-6" style="display: none; padding: 0px;">
                            <button id="btnCancelar" type="button" class="btn btn-info">Cancelar</button>
                            <button id="btnCargar" type="submit" class="btn btn-success">Cargar archivo</button>
                        </div>
                    </div>
                    <div id="divDetalle" class="col-md-6" style="display: none;">
                        <div class="col-md-6" style="padding: 0px;">
                            <div id="divToalReg"> 
                                <label>Total registros en archivo: </label>
                                <span id="totalRegArchivo"></span>
                            </div>
                            <div id="divRegValidos"> 
                                <label>Registros validos a cargar: </label>
                                <span id="regValidos"></span>
                                <input id="registrosValidos" type="hidden" name="registros">
                            </div>
                            <div id="divRegFallidos"> 
                                <label>Registros no validos: </label>
                                <span id="cantidadFallidos"></span>
                            </div>
                            <div id="divDetalleFallidos" style="display: none; height: 150px; overflow-y: scroll;"> 
                                <span id="regFallidos"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </jsp:body>

</base:basePage>