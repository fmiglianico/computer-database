<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="include/header.jsp" />
<div id="main" class="container theme-showcase clearfix">

	<div class="page-header">
		<h1 class="col-sm-offset-2"><spring:message code="page.add.computer.title"/></h1>
	</div>

	<div class="">
	<form:form cssClass="form-horizontal clearfix" commandName="cdto"
			action="addComputer">

			<spring:bind path="name">
				<div class="clearfix ${status.error ? 'has-error' : ''}">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label"><spring:message code="form.name.label"/></label>
						<div class="col-sm-6">
							<spring:message code="form.name.default" var="namePlaceholder"/>
							<form:input type="text" cssClass="form-control" name="name"
								placeholder="${namePlaceholder}" path="name"/>
						</div>
						<div class="col-sm-3 form-control-static">
							<form:errors path="name" cssClass="text-danger" />
						</div>
					</div>
				</div>
			</spring:bind>

			<spring:bind path="introduced">
				<div class="clearfix ${status.error ? 'has-error' : ''}">
					<div class="form-group">
						<label for="introduced" class="col-sm-2 control-label"><spring:message code="form.introduced.label"/></label>
						<div class="col-sm-6">
							<form:input type="date" cssClass="form-control" name="introduced"
								pattern="dd-MM-YYYY" path="introduced"/>
						</div>
						<div class="col-sm-3 form-control-static">
							<form:errors path="introduced" cssClass="text-danger" />
						</div>
					</div>
				</div>
			</spring:bind>

			<spring:bind path="discontinued">
				<div class="clearfix ${status.error ? 'has-error' : ''}">
					<div class="form-group">
						<label for="discontinued" class="col-sm-2 control-label"><spring:message code="form.discontinued.label"/></label>
						<div class="col-sm-6">
							<form:input type="date" cssClass="form-control" name="discontinued"
								pattern="dd-MM-YYYY" path="discontinued"/>
						</div>
						<div class="col-sm-3 form-control-static">
							<form:errors path="discontinued" cssClass="text-danger" />
						</div>
					</div>
				</div>
			</spring:bind>

			<spring:bind path="companyId">
				<div class="clearfix ${status.error ? 'has-error' : ''}">
					<div class="form-group">
						<label for="company" class="col-sm-2 control-label"><spring:message code="form.company.label"/></label>
						<div class="input col-sm-6">
							<form:select name="company" cssClass="form-control" path="companyId">
								<form:option value="" label="--"/>
								<form:options items="${companies}" itemValue="id" itemLabel="name"/>
							</form:select>
						</div>
						<div class="col-sm-3 form-control-static">
							<form:errors path="companyId" cssClass="text-danger" />
						</div>
					</div>
				</div>
			</spring:bind>
			
			<div class="col-sm-offset-2 col-sm-10 actions">
				<spring:message code="page.add.submit" var="submitButtonLabel"/>
				<input type="submit" value="${submitButtonLabel}" class="btn btn-primary">
				<spring:message code="page.add.or"/> <a href="dashboard" class="btn btn-default"><spring:message code="page.add.cancel"/></a>
			</div>

		</form:form>
	</div>
</div>

<hr class="featurette-divider">

<jsp:include page="include/footer.jsp" />