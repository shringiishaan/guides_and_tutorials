
	function sidebar_initialize() {

		if(applicationState.template==="small") {
			applicationState.sideBar.collapse = "true";
			$(document).click(function (event) {
				if(!event.target.matches(".is-sb-toggle")) {
					collapseSideBar();
				}
			});
		}
		else {
			applicationState.sideBar.collapse = "false";
		}

		$(".is-sb > .section").each(initializeSideBarSection);

		$(".is-sb-toggle").click(function() {
			toggleSideBar();
		});
	}

	function toggleSideBar() {
		if(applicationState.sideBar.collapse==="true") {
			expandSideBar();
		}
		else {
			collapseSideBar();
		}
	}

	function getSideBarWidth() {
		if(applicationState.template==="medium") {
			return 250;
		}
		else {
			return 300;
		}
	}

	function expandSideBar() {
		$(".is-sb").css({
			"display":"block",
			"width":"0px"
		});
		$(".is-sb").animate({
			"width":getSideBarWidth()+"px"
		});
		applicationState.sideBar.collapse = "false";
		renderContentForSideBar();
	}

	function collapseSideBar() {
		$(".is-sb").animate({
			"width":"0px"
		}, function() {
			$(".is-sb").css({
				"display":"none"
			});
		});
		applicationState.sideBar.collapse = "true";
		renderContentForSideBar();
	}

	function initializeSideBarSection(index, element)  {

		$(element).children(".label").click(function() {
			toggleSideBarSection($(element).attr("id"));
		});

		if($(element).attr("collapse")==="true") {
			collapseSideBarSection($(element).attr("id"));
		}
		else if($(element).attr("collapse")==="false") {
			expandSideBarSection($(element).attr("id"));
		}
	}

	function toggleSideBarSection(sectionId) {

		if($("#"+sectionId).attr("collapse") === "false") {
			collapseSideBarSection(sectionId);
		} 
		else if ($("#"+sectionId).attr("collapse") === "true") {
			expandSideBarSection(sectionId);
		}
	}

	function collapseSideBarSection(sectionId) {

		$("#"+sectionId + " > .element").slideUp();
		$("#"+sectionId + " > .label > .is-caret").removeClass("fa-caret-up");
		$("#"+sectionId + " > .label > .is-caret").addClass("fa-caret-down");
		$("#"+sectionId).attr("collapse","true");
	}

	function expandSideBarSection(sectionId) {

		$("#"+sectionId + " > .element").slideDown();
		$("#"+sectionId + " > .label > .is-caret").removeClass("fa-caret-down");
		$("#"+sectionId + " > .label > .is-caret").addClass("fa-caret-up");
		$("#"+sectionId).attr("collapse","false");
	}