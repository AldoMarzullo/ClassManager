<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<h3 class="text-muted">
			<spring:message code="message.attendance.courseAttendances" text="Attendances"/> 
			<spring:message code="message.attendance.lecture" text="Lecture"/>
		</h3>
		<h3 class="text-muted">				
			${lecture.number} ${lecture.date} 
			<small> - ${course.name} - ${professor.username}</small>
		</h3> 
		<h3 class="text-muted">				
			${lecture.topic} 
			<small> - ${course.name} - ${professor.username}</small>
		</h3> 	
	</div>
</div>
<div class="row row-content">
	<div class="col-sm-12 col-md-5 col-lg-4">	
		<p class="text-primary">
			<span class="glyphicon glyphicon-search"></span> 
			<spring:message code="message.attendance.idLiveSearch" text="idLiveSearch"/>
		</p>
		<input id="searchID" type="text" class="form-control" placeholder="<spring:message code="message.attendance.idLiveSearch" text="idLiveSearch"/>" />
	</div>
	<div class="col-sm-12 col-md-5 col-lg-4">
		<p class="text-primary">
			<span class="glyphicon glyphicon-search"></span> <spring:message code="message.attendance.surnameLiveSearch" text="surnameLiveSearch"/>
		</p>
		<input id="searchLastName" type="text" class="form-control" placeholder="<spring:message code="message.attendance.surnameLiveSearch" text="surnameLiveSearch"/>" />
	</div>
</div>
<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<form:form action="attendance" method="post" modelAttribute="lecture">
			<div class="table-responsive">
				<table class="tablesorter table table-condensed" id="myTable">
					<thead>
						<tr>
							<th class="sortable">
								<a href="#" style="text-decoration: none;">
									<span class="glyphicon glyphicon-modal-window"></span> 
									<spring:message code="message.attendance.matricola" text="ID"/>
									<span class="glyphicon glyphicon-chevron-up headerSortDown"></span>
									<span class="glyphicon glyphicon-chevron-down headerSortUp"></span>
								</a>
							</th>
							<th class="sortable">
								<a href="#" style="text-decoration: none;">
									<span class="glyphicon glyphicon-user"></span> 
									<spring:message code="message.attendance.students" text="Students"/>
									<span class="glyphicon glyphicon-chevron-up headerSortDown"></span>
									<span class="glyphicon glyphicon-chevron-down headerSortUp"></span>
								</a>
							</th>
							<th><spring:message code="message.attendance.firstName" text="Nome"/></th>
							<th>
								<span class="glyphicon glyphicon-list"></span> 
								<spring:message code="message.attendance.courseAttendances" text="Attendances"/>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${studentsPresent}" var="student">
							<tr>
								<td>${student.serialNumber}</td>
								<td>${student.lastName}</td>
								<td>${student.firstName}</td>
								<td><form:checkbox path="attendanceStudentLectures"	value="${student.username}" checked="checked" /></td>
							</tr>
						</c:forEach>
						<c:forEach items="${studentsNotPresent}" var="student">
							<tr>
								<td>${student.serialNumber}</td>
								<td>${student.lastName}</td>
								<td>${student.firstName}</td>
								<td><form:checkbox path="attendanceStudentLectures"	value="${student.username}" /></td>
							</tr>
						</c:forEach>			
					</tbody>					
				</table>
			</div>
			<input class="btn btn-success pull-right" type="submit" value="<spring:message code="message.attendance.saveChanges" text="ID"/>" />	
		</form:form>
	</div>
</div>