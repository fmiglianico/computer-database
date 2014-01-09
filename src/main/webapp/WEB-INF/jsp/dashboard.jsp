<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<jsp:include page="include/header.jsp" />

<div class="container theme-showcase">
	
	<div class="page-header">
		<h1>${wrap.nbComputers} Computers found</h1>
	</div>
	
	<c:if test="${not empty message}">
		<div class="alert alert-${status}">
			<c:if test="${not empty messageHeader}">
				<strong>${messageHeader}</strong>
			</c:if>
			${message}
		</div>
	</c:if>
	
	<div id="actions">
		<form action="" method="GET" class="form-inline" role="form">
			<div class="form-group">
				<label class="sr-only" for="search">Search</label>
				<input type="search" id="searchbox" name="search"
					value="${wrap.search}" placeholder="Search name" class="form-control">
				<input type="hidden" name="nbrows" value="${wrap.nbRows}"/>
			</div>
			<div class="form-group">
				<input type="submit" id="searchsubmit"
					value="Filter by name"
					class="btn btn-primary" />
			</div>
		</form>
		<cdblib:limitsetter wrap="${wrap}"/>
		<a class="btn btn-primary" id="add" href="addComputer">Add Computer</a>
	</div>
	<p/>
		<table class="computers table table-bordered table-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th id="name">
						<cdblib:link wrap="${wrap}" orderby="name">
							Computer Name
						</cdblib:link>
					</th>
					<th id="intro">
						<cdblib:link wrap="${wrap}" orderby="introduced">
							Introduced Date
						</cdblib:link>
					</th>
					<!-- Table header for Discontinued Date -->
					<th id="disco">
						<cdblib:link wrap="${wrap}" orderby="discontinued">
							Discontinued Date
						</cdblib:link>
					</th>
					<!-- Table header for Company -->
					<th id="company">
						<cdblib:link wrap="${wrap}" orderby="company">
							Company
						</cdblib:link>
					</th>
					<!-- Table header for Buttons -->
					<th id="actionbuttons">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${wrap.list}" var="c">
               <tr>
						<td><a href="editComputer?id=${ c.id }" onclick="">${ c.name }</a></td>
						<td>${ c.displayedIntroduced}</td>
						<td>${ c.displayedDiscontinued}</td>
						<td>${ c.company.name }</td>
						<td>
							<a class="btn btn-sm btn-default" id="edit" href="editComputer?id=${ c.id }">Edit</a>
							<a class="btn btn-sm btn-default" id="delete" href="deleteComputer?id=${ c.id }">Delete</a>
						</td>
					</tr>
   			 </c:forEach>
			</tbody>
		</table>
		<div class="center-block">
			<cdblib:pager wrap="${wrap}" />
		</div>
</div>

<jsp:include page="include/footer.jsp" />
