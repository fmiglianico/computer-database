<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<jsp:include page="include/header.jsp" />
<div id="main" class="container theme-showcase clearfix">

	<div class="page-header">
		<h1 class="col-sm-offset-2">Edit Computer</h1>
	</div>

	<div class="">
		<form:form cssClass="form-horizontal clearfix" commandName="cdto"
			action="editComputer">

			<div class="clearfix ${result.getFieldError('name') == null ? '' : 'has-error'}">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">Computer
						name</label>
					<div class="col-sm-6">
						<form:input type="text" cssClass="form-control" name="name"
							placeholder="Computer Name" path="name"/>
					</div>
					<div class="col-sm-2 form-control-static">
						<form:errors path="name" cssClass="text-danger" />
					</div>
				</div>
			</div>

			<div class="clearfix ${result.getFieldError('introduced') == null ? '' : 'has-error'}">
				<div class="form-group">
					<label for="introduced" class="col-sm-2 control-label">Introduced
						Date</label>
					<div class="col-sm-6">
						<form:input type="date" cssClass="form-control" name="introduced"
							pattern="dd-MM-YYYY" path="introduced"/>
					</div>
					<div class="col-sm-2 form-control-static">
						<form:errors path="introduced" cssClass="text-danger" />
					</div>
				</div>
			</div>

			<div class="clearfix ${result.getFieldError('discontinued') == null ? '' : 'has-error'}">
				<div class="form-group">
					<label for="discontinued" class="col-sm-2 control-label">Discontinued
						Date</label>
					<div class="col-sm-6">
						<form:input type="date" cssClass="form-control" name="discontinued"
							pattern="dd-MM-YYYY" path="discontinued"/>
					</div>
					<div class="col-sm-2 form-control-static">
						<form:errors path="discontinued" cssClass="text-danger" />
					</div>
				</div>
			</div>

			<div class="clearfix ${result.getFieldError('companyId') == null ? '' : 'has-error'}">
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">Company
						Name</label>
					<div class="input col-sm-6">
						<form:select name="company" cssClass="form-control" path="companyId">
							<form:option value="" label="--"/>
							<form:options items="${companies}" itemValue="id" itemLabel="name"/>
						</form:select>
					</div>
					<div class="col-sm-2 form-control-static">
						<form:errors path="companyId" cssClass="text-danger" />
					</div>
				</div>
			</div>
			
			<div class="col-sm-offset-2 col-sm-10 actions">
				<input type="submit" value="Save" class="btn btn-primary">
				or <a href="dashboard" class="btn btn-default">Cancel</a>
			</div>

		</form:form>
	</div>
</div>

<hr class="featurette-divider">

<jsp:include page="include/footer.jsp" />