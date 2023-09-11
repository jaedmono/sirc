<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<ol class="breadcrumb">
	<c:forEach items="${breadcrumb}" var="breadcrumbView"
		varStatus="status">
		<c:choose>
			
			<c:when test="${not status.last}">
				<li class="breadcrumbItem"><a href="${breadcrumbView.link}">${breadcrumbView.code}</a></li>
			</c:when>

			<c:otherwise>
				<li class="active breadcrumbItem">${breadcrumbView.code}</li>
			</c:otherwise>

		</c:choose>
	</c:forEach>
</ol>