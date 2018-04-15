
	function dropdown_initialize() {
		$(".is-dropdown").each(initializeToggleDropDown);
		$(document).click(function(event) {
			if(!event.target.matches(".dd-trigger")) {
				collapseAllDropDowns();
			}
		});
	}

	function initializeToggleDropDown(index, element) {
		$(element).children(".dd-trigger").click(function() {
			toggleDropDown($(element));
		});
	}

	function toggleDropDown(dropDownElement) {
		if(dropDownElement.attr("collapse")===undefined || dropDownElement.attr("collapse")==="true") {
			expandDropDown(dropDownElement);
		}
		else {
			collapseDropDown(dropDownElement);
		}
	}

	function collapseDropDown(dropDownElement) {
		dropDownElement.children(".dd-content").css("display","none");
		dropDownElement.attr("collapse","true");
	}

	function expandDropDown(dropDownElement) {
		dropDownElement.children(".dd-content").css("display","block");
		dropDownElement.attr("collapse","false");
	}

	function collapseAllDropDowns() {
		$(".is-dropdown").each(function(index, element) {
			if($(element).attr("collapse")==="false") {
				collapseDropDown($(element));
			}
		});
	}