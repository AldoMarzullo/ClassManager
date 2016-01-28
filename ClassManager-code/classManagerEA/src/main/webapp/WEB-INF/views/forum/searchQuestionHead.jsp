<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

<script>
 document.title = '<spring:message code="message.forum.searchQuestionTitle" />';
</script>

<link href="/resources/style/uploadFile_css/style.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/resources/style/questions.css" />
<link href="/resources/lib/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet" />

<script src="/resources/lib/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="/resources/lib/bootpag/jquery.bootpag.min.js"></script>
<script src="/resources/script/forum/searchQuestions.js"></script>


<style>
	body {
		background-color: #E6E6E6;
	}
</style>