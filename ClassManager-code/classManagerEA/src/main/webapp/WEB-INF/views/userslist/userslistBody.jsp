<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
				<h3>Users List</h3>
				
				
				<div class="row row-content" style="text-align:right">
					<a href="/gc"><button class="btn btn-primary">Garbage Collector</button></a>
				</div>
				
				<form class="form-inline" action="searchusers" method="GET" role="form">
				  <div class="form-group">
				    <label for="lastname"><spring:message code="message.lastName" text="default text"/></label>
				    <input type="text"  class="form-control" name="query" placeholder="Rossi"/>
				    	<label for="select"><spring:message code="message.usersperpage" text="default text"/></label>
						  <select id="page-size" class="form-control" name="users">
						    <option>10</option>
						    <option>25</option>
						    <option>50</option>
						    <option>100</option>
						  </select>
						  <c:if test="${not empty pageSize}">
							<input id="selected-value" type="hidden" value="${pageSize}"/>
						  </c:if>
				  	<button type="submit" class="btn btn-default">Search</button>
				    	<c:if test="${not empty error}" >
				    		<span class="help-block"><spring:message code="searchField.length.error" text="default text"/></span>
					    	<script>$(".form-group").addClass("has-error")</script>
				    	</c:if>
				  </div>
				    
				</form>
				
				<table class="table table-striped table-bordered">
					<thead>
					      <tr>
					        <th>User
					        	<a href="sort?prop=username">
					        	<c:choose>
					        		<c:when test="${prop == 'username' && asc == true }">
						        		<i class="sort fa fa-sort-asc"></i>
					        		</c:when>
					        		<c:when test="${prop == 'username' && asc != true }">
						        		<i class="sort fa fa-sort-desc"></i>
						        	</c:when>
						        	<c:otherwise>
						        		<i class="sort fa fa-sort"></i>
						        	</c:otherwise>
					        	</c:choose>
					        		
					        	</a>
					        </th>
					        <th>
					        	<spring:message code="message.firstName" text="default text" /> 
					        	<a href="sort?prop=firstName">
						        	<c:choose>
						        		<c:when test="${prop == 'firstName' && asc == true }">
							        		<i class="sort fa fa-sort-asc"></i>
						        		</c:when>
						        		<c:when test="${prop == 'firstName' && asc != true }">
							        		<i class="sort fa fa-sort-desc"></i>
							        	</c:when>
							        	<c:otherwise>
							        		<i class="sort fa fa-sort"></i>
							        	</c:otherwise>
						        	</c:choose>
					        	</a>
					        </th>
					        <th>
					        	<spring:message code="message.lastName" text="default text" /> 
					        	<a href="sort?prop=lastName">
					        		<c:choose>
					        		<c:when test="${prop == 'lastName' && asc == true }">
						        		<i class="sort fa fa-sort-asc"></i>
					        		</c:when>
					        		<c:when test="${prop == 'lastName' && asc != true }">
						        		<i class="sort fa fa-sort-desc"></i>
						        	</c:when>
						        	<c:otherwise>
						        		<i class="sort fa fa-sort"></i>
						        	</c:otherwise>
					        	</c:choose>
					        	</a>
					        </th>
					        <th>
					        	<spring:message code="message.serialNumber" text="default text" /> 
					        	<a href="sort?prop=serialNumber">
					        		<c:choose>
					        		<c:when test="${prop == 'serialNumber' && asc == true }">
						        		<i class="sort fa fa-sort-asc"></i>
					        		</c:when>
					        		<c:when test="${prop == 'serialNumber' && asc != true }">
						        		<i class="sort fa fa-sort-desc"></i>
						        	</c:when>
						        	<c:otherwise>
						        		<i class="sort fa fa-sort"></i>
						        	</c:otherwise>
					        	</c:choose>
					        	</a>
					        </th>
					        <th>
					        	<spring:message code="message.role" text="default text" /> 
					        	<a href="sort?prop=role">
					        		<c:choose>
					        		<c:when test="${prop == 'role' && asc == true }">
						        		<i class="sort fa fa-sort-asc"></i>
					        		</c:when>
					        		<c:when test="${prop == 'role' && asc != true }">
						        		<i class="sort fa fa-sort-desc"></i>
						        	</c:when>
						        	<c:otherwise>
						        		<i class="sort fa fa-sort"></i>
						        	</c:otherwise>
					        	</c:choose>
					        	</a>
					        </th>
					        <th>Action</th>
					      </tr>
    				</thead>
    				<tbody>
    					<c:forEach var="user" items="${users.getPageList()}">
    						
							<tr>
								<td class="user">${user.username}</td>
								<td class="firstname">${user.firstName}</td>
								<td class="lastname">${user.lastName}</td>
								<td class="serialNumber">${user.serialNumber}</td>
								<td class="role">${user.role}</td>
								
								<td>
								<c:if test="${user.username != loggedIn }">
									<button type="submit" class="btn btn-danger"><spring:message code="message.delete" text="default text"/></button>
								</c:if>
								<c:choose>
									<c:when test="${ user.role.equals('Student') }">
										<button type="submit" class="btn btn-success promote"><spring:message code="message.professor" text="default text"/></button>
									</c:when>
									<c:when test="${ user.role.equals('Professor') }">
										<button type="submit" class="btn btn-primary demote"><spring:message code="message.student" text="default text"/></button>
									</c:when>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
    				</tbody>
				</table>
				<form id="options">
					<input name="total-pages" type="hidden" value="${pageCount}"/> 
					<input name="page-number" type="hidden" value="${pageNumber}"/> 
				</form>
				<div id="paginator">
				</div>
				
				
				<div id="result-modal" class="modal fade" tabindex="-1" role="dialog">
					<div class="modal-dialog">
					  <div class="modal-content">
					    <div class="modal-header">
					      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					      <h4 class="modal-title"><spring:message text="default text" code="message.prompttitle"/></h4>
					    </div>
					    <div class="modal-body">
					      <p><spring:message text="default text" code="message.result"/></p>
					    </div>
					    <div class="modal-footer">
					      <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>
					    </div>
					  </div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
				<div id="prompt-modal" class="modal fade" tabindex="-1" role="dialog">
					<div class="modal-dialog">
					  <div class="modal-content">
					    <div class="modal-header">
					      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					      <h4 class="modal-title">Modal title</h4>
					    </div>
					    <div class="modal-body">
					      <p><spring:message text="default text" code="message.prompt"/></p>
					    </div>
					    <div class="modal-footer">
					      <button id="do-delete" type="button" class="btn btn-success" data-dismiss="modal"><spring:message text="default text" code="message.confirm"/></button>
					      <button type="button" class="btn btn-primary" data-dismiss="modal">No</button>
					    </div>
					  </div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
				