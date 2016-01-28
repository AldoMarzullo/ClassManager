<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

		<!-- MODAL addHomework-->
		<c:if test="${not empty homework}">
			<div id="addHomework_modal${modalState}" class="modal fade" role="dialog">
				<form:form id="homework-form" action="/lectures/homeworks" method="POST"
					commandName="homework" role="form">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<h4 id="homeworkTopic_modal" class="modal-title"></h4>
							</div>

							<div class="modal-body">
								<div class="form-group">
									<label for="name"> <spring:message
											code="lectureManager.name" text="default text" />:
									</label>
									<spring:bind path="name">
										<form:input type="text" name="name" path="name" class="form-control" />
										<span class="help-block">${status.errorMessages[0]}</span>
									</spring:bind>
								</div>
								<div class="form-group">
									<label for="description"> <spring:message
											code="lectureManager.description" text="default text" />
									</label>
									<form:input type="text" name="description" path="description"
										class="form-control" />
								</div>

								<form:input id="addHomeworkModal_id" type="hidden" name="id" path="id" class="form-control" />
								<input type="hidden" name="parentId" value="${parentId}"></input>
								
							</div>

							<div class="modal-footer">
								<button type="submit" class="btn btn-success">
									<spring:message code="lectureManager.done" text="default text" />
								</button>
								<button type="button" class="btn btn-default" data-dismiss="modal" onclick="reloadHomework(event)">
									<spring:message code="lectureManager.close" text="default text" />
								</button>
							</div>

						</div>
					</div>
				</form:form>
			</div>
		</c:if>