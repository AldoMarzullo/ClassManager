<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<br>
<div class="row-content">
	<div class="row">
	  <div class="col-lg-6">
	    <div class="input-group">
	      <span class="input-group-addon" id="basic-addon1">
		  	<span class="glyphicon glyphicon-filter"></span>
		  </span>
		  
		  <spring:message code="scores.typeForFilter" text="default text" var="message"/>
	      <input id="filter" name="filterText" type="text" class="form-control" aria-label="..." placeholder="${message}">
	    </div><!-- /input-group -->
	  </div><!-- /.col-lg-6 -->
	</div><!-- /.row -->
	
	<br>
	
	<div class="row-content">		
		<div class="btn-group" data-toggle="buttons">	
			<div class="btn-group" data-toggle="buttons">	
				<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#filters_collapse" aria-expanded="false">
					<spring:message code="scores.moreFilters"/>
				</button>
				<button id="colapse_upload" type="button" class="btn btn-primary" data-toggle="collapse" data-target="#drop" aria-expanded="false">
					<spring:message code="scores.upload"/>
				</button>
			</div>
			<div class="btn-group" data-toggle="buttons">
				<button id="updateTable_btn" type="button" class="btn btn-success" style="display:none">
					<spring:message code="scores.save"/>
				</button>
				<button id="updateTableDismiss_btn" type="button" class="btn btn-danger" style="display:none" onclick="window.location.replace('/scores')">
					<spring:message code="scores.dismiss"/>
				</button>
			</div>
		</div>
	</div>


<div id="drop" class="row-content collapse" style="height: 20%;" draggable="true">
	<spring:message code="scores.dropHere"/>
	<span class="btn btn-default btn-file">
   		 <spring:message code="scores.browse"/>
   		 <input id="uploadInput" type="file" name="myFiles" onchange="uploadAndReplace(this.files);" multiple>
	</span>
</div>

<div class="row collapse" id="filters_collapse">
	  <div class="col-lg-8">
	     <form action="/scores">
		      	<div class="input-group">
		      		
		      		
			 		<span class="input-group-addon"><spring:message code="scores.lecture"/></span>
					<select name="lectureFilter" class="form-control">
				        <c:forEach items="${lectures}" var="item" varStatus="count"> 
				            <option value="${item.id}">${item.number} - ${item.topic}</option>
				        </c:forEach>
				        <option value="" selected><spring:message code="scores.all"/></option>
				    </select> 
				    
			     			
			  		<span class="input-group-addon"><spring:message code="scores.examYear"/></span>
			  		<input value="${yearFilter}" name="yearFilter" type="text" class="form-control" placeholder="exam year..">
			  			
			  		<span class="input-group-btn">
	      				<button id="filter_btn" class="btn btn-success" type="submit">
							<spring:message code="scores.apply"/>
						</button>
	   		  		</span>
	   		  		
	   		  		<span class="input-group-addon"><spring:message code="scores.resPerPage" /></span>
	   		  		<select id="page-size" class="form-control" name="users">
					  <option value="10">10</option>
					  <option value="25">25</option>
					  <option value="50">50</option>
					  <option value="100">100</option>
					</select>
					<c:if test="${not empty pageSize}">
						<input name="pagination" id="selected-value" type="hidden" value="${pageSize}"/>
					</c:if>
	   		  	
		  		</div>

	      </form>
	  </div><!-- /.col-lg-6 -->
	  
	<div class="btn-group" data-toggle="buttons">
	  <label class="btn btn-primary active">
	    <input id="exams_checkbox" type="checkbox" checked><spring:message code="scores.exams"/>
	  </label>
	  <label class="btn btn-primary active">
	    <input id="homeworks_checkbox" type="checkbox" checked><spring:message code="scores.homework"/>
	  </label>
	</div>
</div>
	

<br>
<br>

<div class=row>
	<div class="col-lg-12">
	
		<div class="row">
			<div class="col-md-12 table-responsive">
		<table id="scores_table" class="table table-bordered table-condensed tablesorter" style="white-space:nowrap;}">
			<thead>
				<tr>
					<th class="sortable">	
						<a href="#" style="text-decoration: none;">
							<span class="glyphicon glyphicon-chevron-up headerSortDown"></span>
							<span class="glyphicon glyphicon-chevron-down headerSortUp"></span>
						</a>
					</th>
					<c:forEach items="${homeworks}" var="homework">
						<c:choose>
							<c:when test="${not empty lectureId}">
								<c:if test="${homework.lecture.id == lectureId}">
									<th class="homework-td">${homework.name}									
									</th>
								</c:if>
							</c:when>
							<c:otherwise>
								<th class="homework-td">${homework.name}</th>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<c:forEach items="${exams}" var="exam">
						<c:choose>
							<c:when test="${not empty yearFilter}">
								<fmt:formatDate value="${exam.date}"  type="date"  pattern="yyyy" var="dateYear" />									
								<c:if test="${dateYear == yearFilter}">
									<fmt:formatDate value="${exam.date}"  type="date"  pattern="dd-mm-yyyy" var="formattedDate" />
									<th class="exam-td" style="color:red"><spring:message code="scores.exam"/>: ${formattedDate}</th>
								</c:if>
							</c:when>
							<c:otherwise>
								<fmt:formatDate value="${exam.date}"  type="date"  var="formattedDate" />
								<th class="exam-td" style="color:red"><spring:message code="scores.exam"/>: ${formattedDate}</th>							
							</c:otherwise>
						</c:choose>						
					</c:forEach>
				</tr>	
			</thead>
			
			<tbody class="searchable">
			<c:forEach items="${students}" var="student">
				<tr>
						<td>${student.username}</td>
		
						<c:set var="count" value="0" scope="page" />
						<c:set var="index" value="0" scope="page" />
						<c:set var="solutionsArray" value="${student.homeworkStudentSolvings}" scope="page" />
						
						<c:forEach items="${homeworks}" var="homework">
								<c:set var="count" value="${count + 1}" scope="page" />						
								<c:choose>
									<c:when test="${not empty lectureId}">
										<c:if test="${homework.lecture.id == lectureId}">
											<c:choose>
												<c:when test="${solutionsArray[index].homework.id == homework.id}">
													<td class="homework-td homework" data-type="address" data-pk="${solutionsArray[index].id}">${solutionsArray[index].score}</td>
													<c:set var="index" value="${index + 1}" scope="page" />
												</c:when>
												<c:otherwise>
													<!-- <td class="homework-td"><a data-type="address" class="newHomework" href="#" data-pk="${homework.id} ${student.username}"></a></td>-->
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${solutionsArray[index].homework.id == homework.id}">
												<%-- <td class="homework-td"><a data-type="address" class="homework" href="#" data-pk="${solutionsArray[index].id}">${solutionsArray[index].score}</a></td>--%>
												<td class="homework-td homework" data-type="address" data-pk="${solutionsArray[index].id}">${solutionsArray[index].score}</td>
												<c:set var="index" value="${index + 1}" scope="page" />
											</c:when>
											<c:otherwise>
													 
												<!-- <td class="homework-td"><a data-type="address" class="newHomework" href="#" data-pk="${homework.id} ${student.username}"></a></td>-->
												<td class="homework-td empty"></td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						</c:forEach>
						
						
						<!--<c:forEach begin="${count}" end="${fn:length(homeworks) - 1}" varStatus="loop">
							<td></td>
						</c:forEach>-->
						
						<c:set var="count" value="0" scope="page" />
						<c:set var="index" value="0" scope="page" />
						<c:forEach items="${exams}" var="exam">
							<c:set var="partArray" value="${exam.studentExamPartecipations}" scope="page" />
							<c:set var="count" value="${count + 1}" scope="page" />
								<c:choose>
									<c:when test="${not empty yearFilter}">
										<fmt:formatDate value="${exam.date}"  type="date"  pattern="yyyy" var="dateYear" />
										<c:if test="${dateYear == yearFilter}">											
											<c:set var="check" value="false" scope="page" />
											<c:forEach items="${exam.studentExamPartecipations}" var="part">
													<c:if test="${part.exam.id == exam.id && part.student.username == student.username}">
														<c:if test="${part.praise}"><c:set var="praise" value="L" scope="page"/></c:if>
														<c:if test="${not part.praise}"><c:set var="praise" value="" scope="page"/></c:if>
														<td class="exam-td exam" data-type="address" data-pk="${part.id}">${part.score} ${praise}</td>
														<c:set var="check" value="true" scope="page" />
													</c:if>
											</c:forEach>	
											<c:if test="${check == false}">
												<td class="exam-td newExam" data-type="address" data-pk="${exam.id} ${student.username}"></td>
											</c:if>	
										</c:if>
									</c:when>
									<c:otherwise>
									
										<c:set var="check" value="false" scope="page" />
										<c:forEach items="${exam.studentExamPartecipations}" var="part">
												<c:if test="${part.exam.id == exam.id && part.student.username == student.username}">
													<c:if test="${part.praise}"><c:set var="praise" value="L" scope="page"/></c:if>
													<c:if test="${not part.praise}"><c:set var="praise" value="" scope="page"/></c:if>
													<td class="exam-td exam" data-type="address" data-pk="${part.id}">${part.score} ${praise}</td>
													<c:set var="check" value="true" scope="page" />
												</c:if>
										</c:forEach>	
										<c:if test="${check == false}">
											<td class="exam-td newExam" data-type="address" data-pk="${exam.id} ${student.username}"></td>
										</c:if>		
																		
									</c:otherwise>
								</c:choose>
						</c:forEach>
						
						<c:forEach begin="${count}" end="${fn:length(exams) - 1}" varStatus="loop">
							<td></td>
						</c:forEach>
				</tr>
				
			</c:forEach>
		
		</tbody>
	  </table>
	  </div>
	  </div>
	</div>
	
		<div class="row-content">
			<form id="options">
				<input name="total-pages" type="hidden" value="${pageCount}"/> 
				<input name="page-number" type="hidden" value="${pageNumber}"/> 
			</form>
			<div id="paginator">
			</div>
		</div>
	
</div>
</div>

<style>
td:hover{

	color:#b3d9ff;
	font-weight: bold;
	text-decoration: underline;
}

#drop{
    height:500px;
    line-height:100px;
    border:5px dashed #CCC;

    font-family:Verdana;
    text-align:center;
}

.btn-file {
    position: relative;
    overflow: hidden;
}
.btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 100px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: white;
    cursor: inherit;
    display: block;
}
</style>
