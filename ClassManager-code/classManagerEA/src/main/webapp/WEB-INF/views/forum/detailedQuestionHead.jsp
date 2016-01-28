<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

<script>
 document.title = '${question.getTitle()}';
</script>

<link rel="stylesheet" type="text/css" href="/resources/style/questions.css" />

<script src="/resources/script/forum/detailedQuestions.js"></script>
