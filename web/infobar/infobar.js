
	function infobar_initialize() {
		$(".is-ib > .section").each(initializeInfoBarSection);
	}

	function initializeInfoBarSection(index, element)  {

		$(element).children(".label").click(function() {
			toggleInfoBarSection($(element).attr("id"));
		});

		if($(element).attr("collapse")==="true") {
			collapseInfoBarSection($(element).attr("id"));
		}
		else if($(element).attr("collapse")==="false") {
			expandInfoBarSection($(element).attr("id"));
		}
	}

	function toggleInfoBarSection(sectionId) {

		if($("#"+sectionId).attr("collapse") === "false") {
			collapseInfoBarSection(sectionId);
		} 
		else if ($("#"+sectionId).attr("collapse") === "true") {
			expandInfoBarSection(sectionId);
		}
	}

	function collapseInfoBarSection(sectionId) {

		$("#"+sectionId + " > .element").slideUp();
		$("#"+sectionId + " > .label > .is-caret").removeClass("fa-caret-up");
		$("#"+sectionId + " > .label > .is-caret").addClass("fa-caret-down");
		$("#"+sectionId).attr("collapse","true");
	}

	function expandInfoBarSection(sectionId) {

		$("#"+sectionId + " > .element").slideDown();
		$("#"+sectionId + " > .label > .is-caret").removeClass("fa-caret-down");
		$("#"+sectionId + " > .label > .is-caret").addClass("fa-caret-up");
		$("#"+sectionId).attr("collapse","false");
	}