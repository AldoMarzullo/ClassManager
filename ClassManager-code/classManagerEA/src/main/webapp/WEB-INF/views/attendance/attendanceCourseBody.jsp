<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<h3 class="text-muted"><spring:message code="message.attendance.courseAttendances" text="default text"/> ${course.name} <small> - ${professor.username}</small></h3>
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
		<div class="table-responsive">
			<table class="tablesorter table table-condensed">
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
							<span class="glyphicon glyphicon-book"></span>  
							<spring:message code="message.attendance.lectures" text="Lecture"/>
						</th>
						<c:forEach items="${lectures}" var="lecture">
							<form:form action="attendance" method="get"	modelAttribute="lectureToModify" id="modifyAttendance${lecture.id}">
								<th>
									<form:input path="id" value="${lecture.id}" hidden="true" />
									<a href="#" onclick="document.getElementById('modifyAttendance${lecture.id}').submit();" class="no-text-decoration" style="text-decoration: none;">
										${lecture.number} <span class="glyphicon glyphicon-pencil fa-4x"></span>
									</a>
								</th>
							</form:form>							
						</c:forEach>
					<tr>
				</thead>
				<tbody>
					<c:forEach items="${students}" var="student">
						<tr>
							<td>${student.serialNumber}</td>
							<td>${student.lastName}</td>
							<td>${student.firstName}</td>
							<td></td>
	
							<c:set var="count" value="0" scope="page" />
							<c:set var="index" value="0" scope="page" />
							<c:set var="lecturesArray"
								value="${student.attendanceStudentLectures}" scope="page" />
							<c:forEach items="${lectures}" var="lecture">
								<c:set var="count" value="${count + 1}" scope="page" />
								<c:choose>
									<c:when test="${lecturesArray[index].lecture.number == count}">
										<td><span><i class="glyphicon glyphicon-ok"></i></span></td>
										<c:set var="index" value="${index + 1}" scope="page" />
									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:forEach begin="${count}" end="${fn:length(lectures)}" varStatus="loop">
								<td></td>
							</c:forEach>
						</tr>	
					</c:forEach>	
				</tbody>		
			</table>
		</div>
	</div>
</div>