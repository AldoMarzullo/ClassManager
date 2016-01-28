$(function(){
	$("#login-button").click(function(e) {
		e.preventDefault();
		$("#login-modal").find(".modal-body").load("login",function() {
			$("#login-modal").modal("toggle");
		});
	});
});