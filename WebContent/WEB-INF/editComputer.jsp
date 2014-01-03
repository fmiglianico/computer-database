<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<jsp:include page="include/header.jsp" />
<div id="main" class="container theme-showcase clearfix">

	<div class="page-header">
		<h1 class="col-sm-offset-2">Edit Computer</h1>
	</div>

	<div class="">
		<form class="form-horizontal clearfix" role="form"
			action="editComputer" method="POST">

			<div class="clearfix">
				<div class="form-group ${cdblib:bitwiseAnd(retCode, 1)}">
					<label for="name" class="col-sm-2 control-label">Computer
						name</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="name"
							placeholder="Computer Name" value="${ cdto.name }">
					</div>
				</div>
			</div>

			<div class="clearfix">
				<div class="form-group ${cdblib:bitwiseAnd(retCode, 2)}">
					<label for="introduced" class="col-sm-2 control-label">Introduced
						Date</label>
					<div class="col-sm-6">
						<input type="date" class="form-control" name="introduced"
							value="${ cdto.introduced }" pattern="dd-MM-YYYY" />
					</div>
				</div>
			</div>

			<div class="clearfix">
				<div class="form-group ${cdblib:bitwiseAnd(retCode, 4)}">
					<label for="discontinued" class="col-sm-2 control-label">Discontinued
						Date</label>
					<div class="col-sm-6">
						<input type="date" class="form-control" name="discontinued"
							value="${ cdto.discontinued }" pattern="dd-MM-YYYY" />
					</div>
				</div>
			</div>

			<div class="clearfix">
				<div class="form-group ${cdblib:bitwiseAnd(retCode, 8)}">
					<label for="company" class="col-sm-2 control-label">Company
						Name</label>
					<div class="input col-sm-6">
						<select name="company" class="form-control">
							<option value="0">--</option>
							<c:forEach items="${ companies }" var="c">
								<c:choose>
									<c:when test="${ c.id == cdto.companyId }">
										<option value="${ c.id }" selected>${ c.name }</option>
									</c:when>
									<c:otherwise>
										<option value="${ c.id }">${ c.name }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<div class="col-sm-offset-2 col-sm-10 actions">
				<input type="submit" value="Save" class="btn btn-primary">
				or <a href="dashboard" class="btn btn-default">Cancel</a>
			</div>

		</form>
	</div>
</div>

<hr class="featurette-divider">

<jsp:include page="include/footer.jsp" />