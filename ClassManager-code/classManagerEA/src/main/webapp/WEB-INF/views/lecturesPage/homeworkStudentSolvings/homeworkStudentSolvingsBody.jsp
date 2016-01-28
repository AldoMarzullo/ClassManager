<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12">

		<!-- MODAL VISUALIZE DOC -->
		<div id="visualizer_modal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 id="fileTitle_model" class="modal-title"></h4>
					</div>
					<div class="modal-body">
						<a id="visualizer" href="#"></a>
					</div>
					<div class="modal-footer">
						<button id="download_btn_modal" type="button"
							class="btn btn-default" data-dismiss="modal">
							<spring:message code="lectureManager.download"
								text="default text" />
						</button>
					</div>
				</div>
			</div>
		</div>


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

			<ul class="data">
				<c:choose>
					<c:when test="${not empty files}">
						<c:set var="index" value="0" scope="page"/>  
						<c:set var="contentsArray" value="${contents}" scope="page"/>
						<c:forEach var="file" items="${files}">
							
							<li class="${file.type}">
								<form action="${file.action}">

									<c:if test="${pwd == 'students'}"><input type="hidden" name="username" value="${contentsArray[index].username}"></input></c:if>
									<c:if test="${pwd != 'students'}"><input type="hidden" name="id" value="${contentsArray[index].id}"></input></c:if>
																			
									<input type="hidden" name="path" value="${file.path}"></input>
									<input type="hidden" name="parentId" value="${parentId}"></input>
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
											<c:if test="${not empty pwd}">
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
											<span onclick="delete_${pwd}(event);" class="glyphicon glyphicon-trash" style="color: white"></span>
											<span onclick="alert();" class="glyphicon glyphicon-download-alt" style="color: white"></span>
										</div>
									</c:if>

								</form>
							</li>
							<c:set var="index" value="${index + 1}"/>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="nothingfound">
							<div class="nofiles"></div>
							<span><spring:message code="lectureManager.nofileshere"
									text="default text" /></span>
						</div>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!-- browser -->

	</div>
</div>