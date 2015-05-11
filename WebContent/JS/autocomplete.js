$(function() {
	$("#searchBar").autocomplete({
		max : 10,
		delay : 100,
		minLength : 1,
		autoFocus : true,
		cacheLength : 1,
		scroll : true,
		highlight : false,
		open: function() {
			$(".ui-menu").width($(this).innerWidth());
	    },
		source : function(request, response) {
			$("#divControl").addClass('vertical-row').removeClass('vertical-center-row');
			$("#divControl").css({"padding-top": "30px"});
			$.ajax({
				url : "SearchAutocomplete.cd",
				dataType : "json",
				data : request,
				success : function (data, textStatus, jqXHR) {
					response(data);
				},
				error : function (result) {
					
				}
			});
		},
	    select: function(event, ui) {
	        if (ui.item) {
	            $(event.target).val(ui.item.value);
	        }
	        $(event.target.form).submit();
	    }
	});
});