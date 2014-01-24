<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false" %>

<jsp:include page="include/header.jsp" />

<div id="main" class="container theme-showcase clearfix">
	<div class="page-header">
		<h1 class="col-sm-offset-2">
			<spring:message code="login.title" />
		</h1>
	</div>
	
	<c:if test="${error != null }">
		<div class="alert alert-danger">
			<spring:message code="login.failed" />
		</div>
	</c:if>
	
	<form method="post" id="loginForm" action="<c:url value='j_spring_security_check' />"
		class="form-horizontal clearfix ${error == null ? '' : 'has-error'}">
		<div class="form-group">
			<label for="j_username" class="col-sm-2 control-label">
				<spring:message code="form.username.label" />
			</label>
			<div class="col-sm-6">
				<spring:message code="form.username.default" var="usernamePlaceholder"/>
				<input type="text" name="j_username" id="j_username" class="form-control" placeholder="${usernamePlaceholder}" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="j_password" class="col-sm-2 control-label">
				<spring:message code="form.password.label" />
			</label>
			<div class="col-sm-6">
				<spring:message code="form.password.default" var="passwordPlaceholder"/>
				<input type="password" name="j_password" id="j_password" class="form-control" placeholder="${passwordPlaceholder}"/>
			</div>
		</div>
		
		<div class="col-sm-offset-2 col-sm-10 actions">
			<spring:message code="page.login.submit" var="submitButtonLabel"/>
			<input type="submit" value="${submitButtonLabel}" class="btn btn-primary">
			<spring:message code="page.add.or"/> 
			<a href="dashboard" class="btn btn-default"><spring:message code="page.login.cancel"/></a>
		</div>
		
	</form>
</div>

<hr class="featurette-divider">

<jsp:include page="include/footer.jsp" />
