<%@ tag import="com.formation.computerdb.core.common.Page"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="wrap" required="true" type="com.formation.computerdb.core.common.Page" %>
<p>
<c:if test="${wrap.currPage > 1}">
	<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.nbRows, wrap.search, wrap.orderBy.colName, wrap.orderBy.dir)}&page=${wrap.currPage-1}"> &lt; </a>
 &nbsp;
</c:if>

<c:choose>
	<c:when test="${wrap.nbPages >= 2}">
		<c:forEach var="entry" begin="1" end="2">
			<c:choose>
				<c:when test="${entry == wrap.currPage}">
					<a class="btn btn-sm btn-success" href="#">${entry}</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.nbRows, wrap.search, wrap.orderBy.colName, wrap.orderBy.dir)}&page=${entry}">${entry}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:when test="${wrap.nbPages == 1}">
		<a class="btn btn-sm btn-success" href="#">1</a>
	</c:when>
</c:choose>

<c:if test="${wrap.currPage > 5}">&nbsp;...&nbsp;</c:if>

<c:if test="${wrap.nbPages > 4}">
	<c:forEach var="entry" begin="${cdblib:getMiddleRangeStart(wrap.currPage)}" end="${cdblib:getMiddleRangeEnd(wrap.currPage, wrap.nbPages)}">
		<c:choose>
			<c:when test="${entry == wrap.currPage}">
				<a class="btn btn-sm btn-success" href="#">${entry}</a>
			</c:when>
			<c:otherwise>
				<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.nbRows, wrap.search, wrap.orderBy.colName, wrap.orderBy.dir)}&page=${entry}">${entry}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:if>
 
<c:if test="${wrap.currPage < wrap.nbPages - 4}">&nbsp;...&nbsp;</c:if>

<c:choose>
	<c:when test="${wrap.nbPages >= 4}">
		<c:forEach var="entry" begin="${wrap.nbPages-1}" end="${wrap.nbPages}">
			<c:choose>
				<c:when test="${entry == wrap.currPage}">
					<a class="btn btn-sm btn-success" href="#">${entry}</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.nbRows, wrap.search, wrap.orderBy.colName, wrap.orderBy.dir)}&page=${entry}">${entry}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:when test="${wrap.nbPages == 3}">
		<c:choose>
			<c:when test="${wrap.currPage == 3}">
				<a class="btn btn-sm btn-success" href="#">3</a>
			</c:when>
			<c:otherwise>
				<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.nbRows, wrap.search, wrap.orderBy.colName, wrap.orderBy.dir)}&page=3">3</a>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>

<c:if test="${wrap.currPage < wrap.nbPages}">
	&nbsp;
	<a class="btn btn-sm btn-default" href="${cdblib:generateLink(null, wrap.nbRows, wrap.search, wrap.orderBy.colName, wrap.orderBy.dir)}&page=${wrap.currPage+1}"> &gt; </a>
</c:if>
</p>