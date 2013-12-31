<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="include/header.jsp" />
<section id="main">

	<h1>Edit Computer</h1>

	<form action="editComputer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" value="${ computer.name }" /> <span
						class="help-inline">Required</span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introduced"
						value="${ computer.introduced }" pattern="YY-MM-dd" /> <span
						class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinued"
						value="${ computer.discontinued }" pattern="YY-MM-dd" /> <span
						class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="-1">--</option>
						<c:forEach items="${ companies }" var="c">
							<c:choose>
								<c:when test="${ c.id == computer.company.id }">
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
		</fieldset>
		<div class="actions">
			<input type="submit" value="Save" class="btn primary"> or <a
				href="dashboard" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />