<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:if test="${not empty pageMessageError}">
    <div class="alert alert-danger alert-dismissible" role="alert" style="margin-top: 20px;">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span>${pageMessageError}</span>
    </div>
</c:if>

<c:if test="${not empty pageMessageWarning}">
    <div class="alert alert-warning alert-dismissible" role="alert" style="margin-top: 20px;">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span>${pageMessageWarning}</span>
    </div>
</c:if>

<c:if test="${not empty pageMessageSuccess}">
    <div class="alert alert-success alert-dismissible" role="alert" style="margin-top: 20px;">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span>${pageMessageSuccess}</span>
    </div>
</c:if>

<c:if test="${not empty pageMessageInfo}">
    <div class="alert alert-info alert-dismissible" role="alert" style="margin-top: 20px;">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span>${pageMessageInfo}</span>
    </div>
</c:if>