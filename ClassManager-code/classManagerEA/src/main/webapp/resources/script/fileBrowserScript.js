$(function(){
	var filemanager = $('.filemanager');
	fileList = filemanager.find('.data');

	filemanager.find('.search').click(function(){

		var search = $(this);

		search.find('span').hide();
		search.find('input[type=search]').show().focus();

	});


	// Listening for keyboard input on the search field.
	// We are using the "input" event which detects cut and paste
	// in addition to keyboard input.

	filemanager.find('input').on('input', function(e){

		folders = [];
		files = [];

		var value = this.value.trim();

		if(value.length) {

			filemanager.addClass('searching');

			$(".data li").each(function(index){
				
				var elem = $(this);
				var current = elem.find("form").find("input[name=topic]").val();
				if(current.toLowerCase().indexOf(value.toLowerCase()) == -1){
					elem.hide();
				}
				else
					elem.show();
			});
		}

		else {

			$(".data li").each(function(index){
				$(this).show();
			});
			
			filemanager.removeClass('searching');
		}

	}).on('keyup', function(e){

		// Clicking 'ESC' button triggers focusout and cancels the search

		var search = $(this);

		if(e.keyCode == 27) {

			search.trigger('focusout');

		}

	}).focusout(function(e){

		// Cancel the search

		var search = $(this);

		if(!search.val().trim().length) {

			search.hide();
			search.parent().find('span').show();

		}

	});
});