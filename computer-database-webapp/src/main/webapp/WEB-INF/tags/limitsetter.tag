<%@ tag %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="wrap" required="true" type="org.springframework.data.domain.Page" %>
<%@ attribute name="search" required="false" type="java.lang.String" %>
<%@ attribute name="orderBy" required="false" type="com.formation.computerdb.core.common.OrderBy" %>

<div id="compPerPage">
	<label><spring:message code="dashboard.row.limit.label"/></label>
	<c:choose>
		<c:when test="${wrap.size != 15}">
				<cdblib:link tagclass="btn btn-sm btn-default" nbrows="15" currpage="" wrap="${wrap}" search="${search}" orderBy="${orderBy}">15</cdblib:link>
		</c:when>
		<c:otherwise>
			<a class="btn btn-sm btn-success" href="#">15</a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${wrap.size != 50}">
				<cdblib:link tagclass="btn btn-sm btn-default" nbrows="50" currpage="" wrap="${wrap}" search="${search}" orderBy="${orderBy}">50</cdblib:link>
		</c:when>
		<c:otherwise>
			<a class="btn btn-sm btn-success" href="#">50</a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${wrap.size != 100}">
			<cdblib:link tagclass="btn btn-sm btn-default" nbrows="100" currpage="" wrap="${wrap}" search="${search}" orderBy="${orderBy}">100</cdblib:link>
		</c:when>
		<c:otherwise>
			<a class="btn btn-sm btn-success" href="#">100</a>
		</c:otherwise>
	</c:choose>
</div>