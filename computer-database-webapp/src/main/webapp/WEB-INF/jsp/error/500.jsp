<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../include/header.jsp" />
<div id="main" class="container theme-showcase clearfix">

	
	<div class="jumbotron">
		<div class="page-header">
			<h1><spring:message code="page.error.500.title"/></h1>
		</div>
		<p>
			<spring:message code="page.error.500.message"/>
		</p>
		
		<p>
			<a href="/computerdb/dashboard"><spring:message code="page.error.back.to.dashboard"/></a>
		</p>
	</div>
	
</div>
<jsp:include page="../include/footer.jsp" />