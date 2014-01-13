<%@ tag %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="wrap" required="true" type="com.formation.computerdb.domain.Page" %>

<div id="compPerPage">
	<label><spring:message code="dashboard.row.limit.label"/></label>
	<c:choose>
		<c:when test="${wrap.nbRows != 15}">
				<cdblib:link tagclass="btn btn-sm btn-default" nbrows="15" currpage="" wrap="${wrap}">15</cdblib:link>
		</c:when>
		<c:otherwise>
			<a class="btn btn-sm btn-success" href="#">15</a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${wrap.nbRows != 50}">
				<cdblib:link tagclass="btn btn-sm btn-default" nbrows="50" currpage="" wrap="${wrap}">50</cdblib:link>
		</c:when>
		<c:otherwise>
			<a class="btn btn-sm btn-success" href="#">50</a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${wrap.nbRows != 100}">
			<cdblib:link tagclass="btn btn-sm btn-default" nbrows="100" currpage="" wrap="${wrap}">100</cdblib:link>
		</c:when>
		<c:otherwise>
			<a class="btn btn-sm btn-success" href="#">100</a>
		</c:otherwise>
	</c:choose>
</div>