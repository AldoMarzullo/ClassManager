<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="resources/lib/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="resources/lib/jquery-ui/jquery-ui.js"></script>
<script src="resources/lib/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/lib/bootstrap-3.3.5-dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="resources/style/style.css" />
<link rel="stylesheet" type="text/css"
	href="resources/style/nav-bar.css" />
<link rel="stylesheet" type="text/css"
	href="resources/lib/jquery-ui/jquery-ui.css" />
<script src="resources/script/nav-bar.js"></script>
<script src="resources/script/layout.js"></script>
<title>Class Manager</title>

<jsp:include page="${customHeader}" flush="true" />
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header pull-left">
				<a id="menu-toggle" href="#" class="navbar-toggle"> <span
					class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="navbar-brand" href="#"> Class Manager </a>
			</div>
			<div class="navbar-header pull-right">
				<a class="nav-bar-button logout" href="./db_init"><span
					class="glyphicon glyphicon-log-out"></span>InitDB</a>
				<c:if test="${empty loggedIn}">
					<!--             		<button type="button" class="btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button> -->
					<button type="button" class="btn-info btn-lg" data-toggle="modal"
						data-target="#myModal">Open Modal</button>
					<a id="login-button" class="nav-bar-button login" href="#"><span
						class="glyphicon glyphicon-log-out"></span>Login</a>
					<a class="nav-bar-button logout" href="aldo"><span
						class="glyphicon glyphicon-log-out"></span>Aldo Login</a>
				</c:if>
				<c:if test="${not empty loggedIn}">
					<a class="nav-bar-button logout" href="#"><span
						class="glyphicon glyphicon-log-out"></span> <spring:message
							code="welcome.springmvc" text="default text" /> ${loggedIn}</a>
					<a class="nav-bar-button logout" href="logout"><span
						class="glyphicon glyphicon-log-out"></span> Logout</a>
				</c:if>
				--%>
				<c:if test="${not empty loggedIn && role == 'admin' }">
					<a class="nav-bar-button logout" href="userslist"><span
						class="glyphicon glyphicon-log-out"></span>User List</a>
				</c:if>
			</div>
		</div>
	</nav>

	<div class="container-fluid page-content">
		<div class="row">
			<!-- SIDEBAR -->
			<div class="col-sm-3 col-md-3 col-lg-2">
				<div id="sidebar-wrapper" class="sidebar-desktop">
					<ul class="sidebar-nav">
						<jsp:include page="pageCommons/sideBar.jsp" flush="true" />
					</ul>
				</div>
			</div>
			<!-- END SIDEBAR -->

			<div class="col-sm-9 col-md-9 col-lg-10">
				<jsp:include page="${customBody}" flush="true" />
			</div>

		</div>
	</div>

	<!-- Modal For Login-->
	<div id="login-modal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Login</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	<!-- Modal For Register-->
	<div id="register-modal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message code="message.register" text="default text" />
					</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
</body>
</html>