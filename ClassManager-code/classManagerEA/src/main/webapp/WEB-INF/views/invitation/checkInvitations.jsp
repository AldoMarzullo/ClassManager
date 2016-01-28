<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-12 col-md-12 col-lg-12">
	<br>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<spring:message code="message.invitation.professorOperation2" />
			</h3>
		</div>
		<div class="panel-body">
			<div class="row col-sm-12 col-md-12 col-lg-12">
				<div class="panel panel-default">
					<c:if test="${empty studentList}">
						<br>
						<div class="alert alert-danger" role="alert">
							<strong>Ops!</strong>
							<spring:message code="message.invitation.sendHelpInfo5" />
						</div>
					</c:if>
					<c:if test="${not empty studentList}">
						<br>
						<div class="pull-right">
							<!----------------------->
							<!--   Research Bar    -->
							<!----------------------->
							<form class="form-inline" action="/checkInvitations_research"
								method="post">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										</div>
										<c:if test="${empty researchField}">
											<input type="text" class="form-control" name="researchBar"
												placeholder="Reserch...">
										</c:if>
										<c:if test="${not empty researchField}">
											<input type="text" class="form-control" name="researchBar"
												value="${researchField}" placeholder="Reserch...">
										</c:if>
									</div>
								</div>
								<button type="submit" class="btn btn-primary">
									GO! <span class="glyphicon glyphicon-arrow-right"
										aria-hidden="true"></span>
								</button>
							</form>
							<!----------------------->
							<!-- End Research Bar  -->
							<!----------------------->
						</div>
						<!----------------------->
						<!--Acceptable Student -->
						<!----------------------->
						<table class="table">
							<caption>
								<spring:message code="message.invitation.sendHelpInfo6" />
							</caption>
							<!-- Table Header -->
							<thead>
								<tr>
									<th>#</th>
									<!-- Username -->
									<th><spring:message
											code="message.invitation.sendTableHeadField1" /></th>
									<!-- Course Name -->
									<th><spring:message
											code="message.invitation.sendTableHeadField2" /></th>
									<!-- Serial Number -->
									<th><spring:message
											code="message.invitation.sendTableHeadField3" /></th>
									<!-- Subscription Date -->
									<th><spring:message
											code="message.invitation.sendTableHeadField4" /></th>
									<!-- First Name -->
									<th><spring:message
											code="message.invitation.sendTableHeadField5" /></th>
									<!-- Second Name -->
									<th><spring:message
											code="message.invitation.sendTableHeadField6" /></th>
									<th><form:form id="acceptAll"
											action="checkInvitations_AcceptAll" method="POST">
											<button
												class="btn btn-default invitationActionButton pull-right"
												type="submit">
												<spring:message code="message.invitation.acceptAll" />
											</button>
											<input type="hidden" name="AcceptAll" value="AcceptAll">
										</form:form></th>
								</tr>
							</thead>
							<!-- Generated content -->
							<!-- Table Body -->
							<tbody>
								<c:set var="k" value="0" />
								<c:forEach items="${studentList.list}" var="singleStudent">
									<tr>
										<c:set var="k" value="${k+1}" />
										<th scope="row">${k}</th>
										<!-- Username -->
										<td>${singleStudent.field1}</td>
										<!-- Course Name -->
										<td>${singleStudent.field2}</td>
										<!-- Serial Number -->
										<td>${singleStudent.field3}</td>
										<!-- Subscription Date -->
										<td>${singleStudent.field4}</td>
										<!-- First Name -->
										<td>${singleStudent.field5}</td>
										<!-- Second Name -->
										<td>${singleStudent.field6}</td>
										<td><form:form id="accept${k}"
												action="checkInvitations_AcceptSingle" method="POST">
												<button
													class="btn btn-default invitationActionButton pull-right"
													type="submit">
													<spring:message code="message.invitation.acceptSingle" />
												</button>
												<input type="hidden" name="studentName"
													value="${singleStudent.field1}">
												<input type="hidden" name="courseName"
													value="${singleStudent.field2}">
											</form:form></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!---------------------------->
						<!-- End Acceptable Student -->
						<!---------------------------->
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>