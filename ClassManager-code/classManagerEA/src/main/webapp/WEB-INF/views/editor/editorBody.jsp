<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false" %>

<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12 ">
		
			<div class="row">
				<div class="col-md-2">
				
				<div class="form-group">
				<label for="editorLang"><spring:message code="message.editor.language"/></label>
				<select id="editorLang" class="form-control">
					<c:forEach var="tmpLang" items="${aviableLangs}">
						<option value="${tmpLang.getValue() }">${tmpLang.getLabel() }</option>							
					</c:forEach>
		            <option value="javascript">Javascript</option>
		          </select> 
		          </div>
		          
		          <div class="form-group">
		          <label for="editorTheme"><spring:message code="message.editor.theme"/></label>
		          <select id="editorTheme" class="form-control">
		
		            <option value="monokai">Monokai</option>
		            <option value="chrome">Chrome</option>
		            <option value="clouds">Clouds</option>
		            <option value="clouds_midnight">Clouds Midnight</option>
		            <option value="cobalt">Cobalt</option>
		            <option value="crimson_editor">Crimson Editor</option>
		            <option value="dawn">Dawn</option>
		            <option value="eclipse">Eclipse</option>
		            <option value="idle_fingers">Idle Fingers</option>
		            <option value="kr_theme">Kr Theme</option>
		            <option value="merbivore">Merbivore</option>
		            <option value="merbivore_soft">Merbivore Soft</option>
		            <option value="mono_industrial">Mono Industrial</option>
		            <option value="pastel_on_dark">Pastel On Dark</option>
		            <option value="solarized_dark">Solarized Dark</option>
		            <option value="solarized_light">Solarized Light</option>
		            <option value="textmate">TextMate</option>
		            <option value="tomorrow">Tomorrow</option>
		            <option value="tomorrow_night">Tomorrow Night</option>
		            <option value="tomorrow_night_blue">Tomorrow Night Blue</option>
		            <option value="tomorrow_night_bright">Tomorrow Night Bright</option>
		            <option value="tomorrow_night_eighties">Tomorrow Night Eighties</option>
		            <option value="twilight">Twilight</option>
		            <option value="vibrant_ink">Vibrant Ink</option>
		          </select>
		          </div>
		         
		        <div class="form-group">
		        <input type="button" id="tryCodeBtn" class="btn btn-primary btn-block" value="<spring:message code="message.editor.executeCode"/>" />
		        <input type="button" id="toggleConsole" class="btn btn-primary btn-block" value="<spring:message code="message.editor.toggleConsole"/>" />
		        <input type="button" id="clearConsole" class="btn btn-primary btn-block" value="<spring:message code="message.editor.clearConsole"/>" />
				</div>
				
				
				</div>
				<div class="col-md-10">
					<div class="row">
						<div class="col-md-12">
							<div class="editor" id="editor"></div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12">
							<div class="editor" id="console"></div>
						</div>
					</div>
					
					<form:form id="editorForm" action="/editor/editor" method="post" commandName="status" acceptCharset="utf-8">
						<form:textarea id="codeTextarea" path="code"></form:textarea>
						<form:textarea id="consoleTextarea" path="consoleContent"></form:textarea>
						<form:input id="themeInput" path="theme"/>
						<form:input id="languageInput" path="language"/>
					</form:form>
				
				</div>
			</div>
		
	</div>
</div>