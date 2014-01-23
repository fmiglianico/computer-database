<%@ tag %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="wrap" required="true" type="org.springframework.data.domain.Page" %>
<%@ attribute name="search" required="false" type="java.lang.String" %>
<%@ attribute name="orderBy" required="false" type="com.formation.computerdb.core.common.OrderBy" %>

<p>
<c:if test="${wrap.number > 0}">
	<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.size, search, orderBy.colName, orderBy.dir)}&page=${wrap.number}"> &lt; </a>
 &nbsp;
</c:if>

<c:choose>
	<c:when test="${wrap.totalPages >= 2}">
		<c:forEach var="entry" begin="1" end="2">
			<c:choose>
				<c:when test="${entry - 1 == wrap.number}">
					<a class="btn btn-sm btn-success" href="#">${entry}</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.size, search, orderBy.colName, orderBy.dir)}&page=${entry}">${entry}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:when test="${wrap.totalPages == 1}">
		<a class="btn btn-sm btn-success" href="#">1</a>
	</c:when>
</c:choose>

<c:if test="${wrap.number + 1 > 5}">&nbsp;...&nbsp;</c:if>

<c:if test="${wrap.totalPages > 4}">
	<c:forEach var="entry" begin="${cdblib:getMiddleRangeStart(wrap.number + 1)}" end="${cdblib:getMiddleRangeEnd(wrap.number + 1, wrap.totalPages)}">
		<c:choose>
			<c:when test="${entry - 1 == wrap.number}">
				<a class="btn btn-sm btn-success" href="#">${entry}</a>
			</c:when>
			<c:otherwise>
				<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.size, search, orderBy.colName, orderBy.dir)}&page=${entry}">${entry}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:if>
 
<c:if test="${wrap.number + 1 < wrap.totalPages - 4}">&nbsp;...&nbsp;</c:if>

<c:choose>
	<c:when test="${wrap.totalPages >= 4}">
		<c:forEach var="entry" begin="${wrap.totalPages-1}" end="${wrap.totalPages}">
			<c:choose>
				<c:when test="${entry - 1 == wrap.number}">
					<a class="btn btn-sm btn-success" href="#">${entry}</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.size, search, orderBy.colName, orderBy.dir)}&page=${entry}">${entry}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:when test="${wrap.totalPages == 3}">
		<c:choose>
			<c:when test="${wrap.number == 2}">
				<a class="btn btn-sm btn-success" href="#">3</a>
			</c:when>
			<c:otherwise>
				<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.size, search, orderBy.colName, orderBy.dir)}&page=3">3</a>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>

<c:if test="${wrap.number  < wrap.totalPages - 1}">
	&nbsp;
	<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.size, search, orderBy.colName, orderBy.dir)}&page=${wrap.number+2}"> &gt; </a>
</c:if>
</p>