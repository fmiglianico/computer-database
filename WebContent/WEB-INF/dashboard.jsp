<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<jsp:include page="include/header.jsp" />

<section id="main">
	<h1 id="homeTitle"> ${ncomputers} Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="hidden" name="max" value="${max}"/>
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<div id="compPerPage">
			<label>Computers per page:</label>
			<c:choose>
				<c:when test="${max != 15}">
 					<cdblib:link tagclass="btn" max="15" search="${search}" orderby="${orderby}" dir="${dir}">15</cdblib:link>
				</c:when>
				<c:otherwise>
					<a class="btn success" href="#">15</a>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${max != 50}">
 					<cdblib:link tagclass="btn" max="50" search="${search}" orderby="${orderby}" dir="${dir}">50</cdblib:link>
				</c:when>
				<c:otherwise>
					<a class="btn success" href="#">50</a>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${max != 100}">
					<cdblib:link tagclass="btn" max="100" search="${search}" orderby="${orderby}" dir="${dir}">100</cdblib:link>
				</c:when>
				<c:otherwise>
					<a class="btn success" href="#">100</a>
				</c:otherwise>
			</c:choose>
		</div>
		<a class="btn success" id="add" href="addComputer">Add Computer</a>
	</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th id="name">
						<c:choose>
							<c:when test="${orderby == 'name'}">
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="name" dir="${oppdir}">
									Computer Name
								</cdblib:link>
							</c:when>
							<c:otherwise>
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="name" dir="asc">
									Computer Name
								</cdblib:link>
							</c:otherwise>
						</c:choose>
					</th>
					<th id="intro">
						<c:choose>
							<c:when test="${orderby == 'intro'}">
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="intro" dir="${oppdir}">
									Introduced Date
								</cdblib:link>
							</c:when>
							<c:otherwise>
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="intro" dir="asc">
									Introduced Date
								</cdblib:link>
							</c:otherwise>
						</c:choose>
					</th>
					<!-- Table header for Discontinued Date -->
					<th id="disco">
						<c:choose>
							<c:when test="${orderby == 'disco'}">
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="disco" dir="${oppdir}">
									Discontinued Date
								</cdblib:link>
							</c:when>
							<c:otherwise>
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="disco" dir="asc">
									Discontinued Date
								</cdblib:link>
							</c:otherwise>
						</c:choose>
					</th>
					<!-- Table header for Company -->
					<th id="company">
						<c:choose>
							<c:when test="${orderby == 'company'}">
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="company" dir="${oppdir}">
									Company
								</cdblib:link>
							</c:when>
							<c:otherwise>
								<cdblib:link max="${max}" page2="${page}" search="${search}" orderby="company" dir="asc">
									Company
								</cdblib:link>
							</c:otherwise>
						</c:choose>
					</th>
					<!-- Table header for Buttons -->
					<th id="actionbuttons">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${computers}" var="c">
               <tr>
						<td><a href="editComputer?id=${ c.id }" onclick="">${ c.name }</a></td>
						<td>${ c.introduced }</td>
						<td>${ c.discontinued }</td>
						<td>${ c.company.name }</td>
						<td>
							<a class="btn" id="edit" href="editComputer?id=${ c.id }">Edit</a>
							<a class="btn" id="delete" href="deleteComputer?id=${ c.id }">Delete</a>
						</td>
					</tr>
   			 </c:forEach>
			</tbody>
		</table>
		<div class="center-block">
			<cdblib:pager nbPages="${nbPages}" currPage="${page}" link="${cdblib:generateLink(null, max, search, orderby, dir)}"/>
		</div>
</section>

<jsp:include page="include/footer.jsp" />
