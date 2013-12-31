<%@ tag %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="nbPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="currPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="link" required="true"%>

<c:if test="${currPage > 1}">
	<a class="btn" href="${link}&page=${currPage-1}"> &lt; </a>
 &nbsp;
</c:if>

<c:choose>
	<c:when test="${nbPages >= 2}">
		<c:forEach var="entry" begin="1" end="2">
			<c:choose>
				<c:when test="${entry == currPage}">
					<a class="btn success" href="#">${entry}</a>
				</c:when>
				<c:otherwise>
					<a class="btn" href="${link}&page=${entry}">${entry}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:when test="${nbPages == 1}">
		<a class="btn success" href="#">1</a>
	</c:when>
</c:choose>

<c:if test="${currPage > 5}">&nbsp;...&nbsp;</c:if>

<c:if test="${nbPages > 4}">
	<c:forEach var="entry" begin="${cdblib:getMiddleRangeStart(currPage)}" end="${cdblib:getMiddleRangeEnd(currPage, nbPages)}">
		<c:choose>
			<c:when test="${entry == currPage}">
				<a class="btn success" href="#">${entry}</a>
			</c:when>
			<c:otherwise>
				<a class="btn" href="${link}&page=${entry}">${entry}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:if>
 
<c:if test="${currPage < nbPages - 4}">&nbsp;...&nbsp;</c:if>

<c:choose>
	<c:when test="${nbPages >= 4}">
		<c:forEach var="entry" begin="${nbPages-1}" end="${nbPages}">
			<c:choose>
				<c:when test="${entry == currPage}">
					<a class="btn success" href="#">${entry}</a>
				</c:when>
				<c:otherwise>
					<a class="btn" href="${link}&page=${entry}">${entry}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:when test="${nbPages == 3}">
		<c:choose>
			<c:when test="${currPage == 3}">
				<a class="btn success" href="#">3</a>
			</c:when>
			<c:otherwise>
				<a class="btn" href="${link}&page=3">3</a>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>

<c:if test="${currPage < nbPages}">
	&nbsp;
	<a class="btn" href="${link}&page=${currPage+1}"> &gt; </a>
</c:if>