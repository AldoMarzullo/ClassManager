<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

<script>
 document.title = '<spring:message code="message.forum.insertAnswerTitle"/>';
</script>

<link href="/resources/style/uploadFile_css/style.css" rel="stylesheet" />

<script type="text/javascript" src="/resources/script/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/resources/script/forum/insertAnswer.js"></script>


<script src="/resources/script/fileBrowserScript.js"></script>
<script src="/resources/lib/uploadFile-lib/jquery.knob.js"></script>

<script src="/resources/lib/uploadFile-lib/jquery.ui.widget.js"></script>
<script src="/resources/lib/uploadFile-lib/jquery.iframe-transport.js"></script>
<script src="/resources/lib/uploadFile-lib/jquery.fileupload.js"></script>

<script src="/resources/script/forum/uploadAttachment.js"></script>

<style>
	body {
		background-color: #E6E6E6;
	}
</style>