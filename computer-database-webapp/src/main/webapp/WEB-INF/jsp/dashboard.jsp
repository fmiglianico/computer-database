<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<jsp:include page="include/header.jsp" />

<div class="container theme-showcase">
	
	<div class="page-header">
		<h1><spring:message code="dashboard.title" arguments="${wrap.totalElements}"/></h1>
	</div>
	
	<c:if test="${not empty message}">
		<div class="alert alert-${status}">
			<spring:message code="dashboard.alert.${message}.${status}" />
		</div>
	</c:if>
	
	<div id="actions">
		<form action="" method="GET" class="form-inline">
			<div class="form-group">
				<label class="sr-only" for="search">Search</label>
				<spring:message code="dashboard.search.placeholder" var="searchPlaceholder"/>
				<input type="search" id="searchbox" name="search"
					value="${search}" placeholder="${searchPlaceholder}" class="form-control">
				<input type="hidden" name="nbrows" value="${wrap.size}"/>
			</div>
			<div class="form-group">
				<spring:message code="dashboard.search.button" var="searchButton"/>
				<input type="submit" id="searchsubmit"
					value="${searchButton}"
					class="btn btn-primary" />
			</div>
		</form>
		<cdblib:limitsetter wrap="${wrap}" search="${search}" orderBy="${orderBy}"/>
		<a class="btn btn-primary" id="add" href="addComputer"><spring:message code="dashboard.add.computer.button"/></a>
	</div>
	<p/>
		<table class="computers table table-bordered table-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th id="name">
						<cdblib:link wrap="${wrap}" newOrderBy="name" search="${search}" orderBy="${orderBy}">
							<spring:message code="dashboard.table.computer.name"/>
						</cdblib:link>
					</th>
					<th id="intro">
						<cdblib:link wrap="${wrap}" newOrderBy="introduced" search="${search}" orderBy="${orderBy}">
							<spring:message code="dashboard.table.introduced"/>
						</cdblib:link>
					</th>
					<!-- Table header for Discontinued Date -->
					<th id="disco">
						<cdblib:link wrap="${wrap}" newOrderBy="discontinued" search="${search}" orderBy="${orderBy}">
							<spring:message code="dashboard.table.discontinued"/>
						</cdblib:link>
					</th>
					<!-- Table header for Company -->
					<th id="company">
						<cdblib:link wrap="${wrap}" newOrderBy="company" search="${search}" orderBy="${orderBy}">
							<spring:message code="dashboard.table.company"/>
						</cdblib:link>
					</th>
					<!-- Table header for Buttons -->
					<th id="actionbuttons"><spring:message code="dashboard.table.actions"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${wrap.content}" var="c">
               <tr>
						<td><a href="editComputer?id=${ c.id }" onclick="">${ c.name }</a></td>
						<spring:message code="date.format" var="dateFormat"/>
						<td><joda:format value="${c.introduced}" pattern="${dateFormat}"/></td>
						<td><joda:format value="${c.discontinued}" pattern="${dateFormat}"/></td>
						<td>${c.company.name }</td>
						<td>
							<a class="btn btn-sm btn-default" id="edit" href="editComputer?id=${ c.id }"><spring:message code="dashboard.table.actions.edit"/></a>
							<a class="btn btn-sm btn-default" id="delete" href="deleteComputer?id=${ c.id }"><spring:message code="dashboard.table.actions.delete"/></a>
						</td>
					</tr>
   			 </c:forEach>
			</tbody>
		</table>
		<div class="center-block">
			<cdblib:pager wrap="${wrap}" search="${search}"/>
		</div>
</div>

<jsp:include page="include/footer.jsp" />
