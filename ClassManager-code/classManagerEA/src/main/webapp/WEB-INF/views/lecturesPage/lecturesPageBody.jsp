<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12">

		<jsp:include page="modals/visualizeModal.jsp" flush="true" />
		<c:if test="${not empty canCreate}">
			<jsp:include page="modals/createLecture.jsp" flush="true" />
			<jsp:include page="modals/createHomework.jsp" flush="true" />
		</c:if>


		<!-- browser -->
		<div class="filemanager">

			<div class="search">
				<input type="search" placeholder="Find a file.." />
				<span class="glyphicon glyphicon-search" style="color: white"></span>
			</div>
			<div class="breadcrumbs">
				<a href="${backPage}">
					<span class="glyphicon glyphicon-hand-left" style="color: white"></span>
				</a>
			</div>

			<div class="row row-content">

				<c:if test="${not empty lecture && not empty canCreate}">
					<div id="createNewClass_btn" class="btn btn-default">
						<spring:message code="lectureManager.addNewLecture"
							text="default text" />
					</div>
				</c:if>
				<c:if test="${not empty pwd && pwd != 'lectures' && pwd != 'homeworks'  && not empty canCreate && canCreate == true}">
					<div id="uploadFile_btn" class="btn btn-default">
						<spring:message code="lectureManager.upload" text="default text" />
					</div>
				</c:if>
				<c:if test="${not empty homework  && not empty canCreate && canCreate}">
					<div id="addHomework_btn" class="btn btn-default">
						<spring:message code="lectureManager.addHomework"
							text="default text" />
					</div>
				</c:if>

				<div id="uploadFile_div" style="display: none;">
					<form id="upload" method="post" action="/lectures/upload_${pwd}"
						enctype="multipart/form-data" onsubmit="return reload();">
						<div id="drop">
							<spring:message code="lectureManager.drophere"
								text="default text" />
							<a> <spring:message code="lectureManager.browse"
									text="default text" />
							</a> <input type="file" name="file" multiple />
						</div>
						<ul>
							<!-- The file uploads will be shown here -->
						</ul>
						<div class="btn btn-default" id="file_upload_btn"
							style="margin-top: 15px">
							<spring:message code="lectureManager.done" text="default text" />
						</div>
						<input id="toUpload_input" type="hidden" name="parentId"
							value="${parentId}" />
					</form>
				</div>
			</div>

			<ul class="data">
				<c:choose>
					<c:when test="${not empty files && fn:length(files) gt 0}">
					
						<c:set var="index" value="0" scope="page"/>  
						<c:set var="contentsArray" value="${contents}" scope="page"/>
						<c:forEach var="file" items="${files}">
							
							<li class="${file.type}">
								<form action="${file.action}">

									<input type="hidden" name="id" value="${contentsArray[index].id}"></input> 
									
									<c:if test="${not empty lecture}">
										<input type="hidden" name="topic" value="${contentsArray[index].topic}"></input>
										<input type="hidden" name="date" value="${contentsArray[index].date}"></input>
										<input type="hidden" name="beginHour" value="${contentsArray[index].beginHour}"></input>
										<input type="hidden" name="endHour" value="${contentsArray[index].beginHour}"></input>
										<input type="hidden" name="classroom" value="${contentsArray[index].classroom}"></input>
									</c:if>
									
									<c:if test="${not empty homework}"><input type="hidden" name="name" value="${contentsArray[index].name}"></input></c:if>
									
									<%-- <input type="hidden" name="description" value="${contentsArray[index].description}"></input> --%>
									<input type="hidden" name="path" value="${file.path}"></input>
									<input type="hidden" name="parentId" value="${file.parentId}"></input>
									<input type="submit" style="display: none;"></input>

									<c:if test="${file.type == 'folder'}">

										<div onClick="this.parentNode.submit()" title="${file.name}"
											class="${file.type}">
											<span class="icon ${file.type}"></span> <span class="name">${file.name}</span>
											<span class="details"> <c:choose>
													<c:when test="${file.childs != 0}">
														${file.childs} <spring:message code="lectureManager.items"
															text="default text" />
													</c:when>
													<c:otherwise>
														<spring:message code="lectureManager.empty"
															text="default text" />
													</c:otherwise>
												</c:choose>
											</span> 
											<c:if test="${not empty pwd  && not empty canCreate}">
												<span onclick="delete_${pwd}(event);" class="glyphicon glyphicon-trash" style="color: white"></span>
												<span onclick="update_${pwd}(event);" class="glyphicon glyphicon-pencil" style="color: white"></span>
											</c:if>
										</div>
									</c:if>

									<c:if test="${file.type == 'file'}">
										<div title="${file.name}" class="${file.type}">
											<span class="icon ${file.type} f-${file.extension}">.${file.extension}</span>
											<span class="name">${file.name}</span>
											<span class="details">${file.size} Bytes</span>
											<c:if test="${not empty canCreate}"><span onclick="delete_${pwd}(event);" class="glyphicon glyphicon-trash" style="color: white"></span></c:if>
											<span onclick="download(event)" class="glyphicon glyphicon-download-alt" style="color: white"></span>
										</div>
									</c:if>

								</form>
							</li>
							<c:set var="index" value="${index + 1}"/>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="nothingfound" style="display:block;">
							<div class="nofiles"></div>
							<span><spring:message code="lectureManager.nofileshere" text="default text" /></span>
						</div>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!-- browser -->



	</div>
</div>

<script>
$(document).ready(function() 
{	

	$('#createNewClass_modal_open').modal('show');
	
	$('#addHomework_modal_open').modal('show');
});
</script>