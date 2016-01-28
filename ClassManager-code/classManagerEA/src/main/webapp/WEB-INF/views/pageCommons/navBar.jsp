<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<nav class="navbar navbar-fixed-top navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header pull-left">
			<a id="menu-toggle" href="#" class="navbar-toggle"> <span
				class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</a>
			<a class="navbar-brand" href="/">
			<span class="glyphicon glyphicon-blackboard"></span> Class Manager 
			</a>
		</div>
		<button type="button" class="navbar-toggle pull-right navbar-menu-custom" data-toggle="collapse" data-target=".navbar-collapse">	      
	      	<span class="icon-bar"></span>
	      	<span class="icon-bar"></span>
	      	<span class="icon-bar"></span>
   		</button>
		<div id="secondCollapse" class="pull-right collapse navbar-collapse">
		 <ul class="nav navbar-nav">
			<li class="nav-bar-button dropdown keep-open pull-right hidden-xs">						
				<input hidden="true" id="websocketUser" value="${loggedIn}" />		
				<a id="notificationDropDown" onclick="resetBadge()" class="dropdown-toggle" href="#" data-toggle="dropdown" aria-as-popup="true" aria-expanded="false" data-target="dropdown-menu">
					<span class="glyphicon glyphicon-globe color-text-white" style="top:1px;">
					</span>
					<span id="badge" class="badge"></span>
				</a>
				<ul class="dropdown-menu notificationsWidth" >
					<div id="panelNotificationsNew"></div>
					<div id="panelNotificationsOld"></div>
				</ul>			
			</li>
			<a class="nav-bar-button logout" href="./db_init"><span
				class="glyphicon glyphicon-hdd"></span> InitDB</a>
			<c:if test="${empty loggedIn}">
				<!--             		<button type="button" class="btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button> -->
				<a id="login-button" class="nav-bar-button login" href="#"><span
					class="glyphicon glyphicon-log-in"></span> Login</a>
				<a class="nav-bar-button logout" href="aldo"><span
					class="glyphicon glyphicon-log-out"></span> Aldo Login</a>
			</c:if>
			<c:if test="${not empty loggedIn}">
				<a class="nav-bar-button logout" href="#"><span
					class="glyphicon glyphicon-user"></span> <spring:message
						code="welcome.springmvc" text="default text" /> ${loggedIn}</a>
				<a class="nav-bar-button logout" href="/logout"><span
					class="glyphicon glyphicon-log-out"></span> Logout</a>
			</c:if>
		</div>
	</div>
</nav>