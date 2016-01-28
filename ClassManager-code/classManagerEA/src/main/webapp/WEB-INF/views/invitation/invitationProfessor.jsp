<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-12 col-md-12 col-lg-12">
	<br>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<spring:message code="message.invitation.operation" />
			</h3>
		</div>
		<div class="panel-body">
			<!-- Available Operations -->
			<ul>
				<li><a href="/sendInvitation"><spring:message
							code="message.invitation.professorOperation1" /></a></li>
				<li><a href="/checkInvitations"><spring:message
							code="message.invitation.professorOperation2" /></a></li>
			</ul>
			<!-- End Available Operations -->
		</div>
	</div>
</div>
