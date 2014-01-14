<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="header.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/static/css/bootstrap.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/static/css/bootstrap-theme.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/static/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
	<div class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="dashboard"><spring:message code="header.application"/></a>
			</div>
			<div class="navbar-collapse collapse">
				<div class="navbar-right">
					<a href="?lang=fr_FR">
						<img src="<c:url value='/static/img/fr_FR.png'/>" alt="FR" class="img-flag"/>
					</a>
					&nbsp;
					<a href="?lang=en_US">
						<img src="<c:url value='/static/img/en_US.png'/>" alt="EN" class="img-flag"/>
					</a>
				</div>
			</div>
		</div>
	</div>