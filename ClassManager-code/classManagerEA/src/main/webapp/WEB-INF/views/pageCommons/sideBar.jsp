<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li><a href="/courses"><span class="glyphicon glyphicon-book"></span> <spring:message text="default text" code="message.courses"/></a></li>

<c:if test="<%=session.getAttribute(\"ActiveCourse\") != null%>">
	<li><a href="/lectures"><span
			class="glyphicon glyphicon-folder-open"> </span> <spring:message text="default text" code="message.lectures"/></a></li>

	<li><a href="/scores"><span
			class="glyphicon glyphicon-equalizer"></span> <spring:message text="default text" code="message.scores"/></a></li>

	<li><a href="/students"> <span class="glyphicon glyphicon-edit"></span> <spring:message text="default text" code="message.homeworks"/></a></li>
	
	<li><a href="/view_attendance"> <span class="glyphicon glyphicon-list"></span> <spring:message text="default text" code="message.attendance"/></a></li>
</c:if>

<li><a href="/calendar"><span
		class="glyphicon glyphicon-calendar"></span> <spring:message text="default text" code="message.calendar"/></a></li>

<li><a href="/statistics"><span
		class="glyphicon glyphicon-stats"></span> <spring:message text="default text" code="message.statistics"/></a></li>

<li><a href="/invitation"> <span
		class="glyphicon glyphicon-gift" aria-hidden="true"></span> <spring:message text="default text" code="message.invitation"/>
		<c:if test="${not empty newInvitations}">
			<span class="badge invitationActionButton">${newInvitations}</span>
		</c:if>
</a></li>

<li><a href="/viewer3D"><span
		class="glyphicon glyphicon-eye-open"></span> <spring:message text="default text" code="message.viewer"/> <span class="glyphicon glyphicon-wrench"></span></a></li>

<li><a href="/noticeboard"> <span
		class="glyphicon glyphicon-send"></span> <spring:message
			code="message.noticeboard" text="default text" />
</a></li>

<li><a href="/forum"> <span class="glyphicon glyphicon-book"></span>
		Forum
</a></li>
<li><a href="/editor"> <span class="glyphicon glyphicon-modal-window"></span>
		Editor
</a></li>
<li><a href="/blackboard"> <span class="glyphicon glyphicon-blackboard"></span>
		<spring:message text="default text" code="message.blackboard"/> <span class="glyphicon glyphicon-wrench"></span>
</a></li>
<c:if test="${not empty loggedIn && role == 'admin' }">
	<li><a href="/userslist"> <span class="glyphicon glyphicon-king"></span>
		<spring:message text="default text" code="message.administration"/>
	</a></li>
</c:if>
