<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

	<div class="row row-content">
		<div class="col-sm-12 col-md-12 col-lg-12">

			<script type="text/javascript">
			var TogetherJSConfig_dontShowClicks = true;
			var TogetherJSConfig_getUserName = function () {return '${username}';};
			</script>
		
			<script src="https://togetherjs.com/togetherjs-min.js"></script>
			<button onclick="TogetherJS(this); return false;">Start TogetherJS</button>
			<button id="clearBtn">Clear</button>
		
			<div id="sketchContainer" style="width: 70%; height: 300px; border: 1px solid rgba(0,0,0,0.2)">
				<canvas id="sketch"></canvas>
			</div>


		</div>
	</div>
				
