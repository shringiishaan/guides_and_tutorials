

	function modal_initialize() {
		$(".is-modal-open").each(initializeModalOpenTrigger);
		$(".is-modal-close").each(initializeModalCloseTrigger);
	}

	function initializeModalOpenTrigger(index, element) {
		$(element).click(function(){
			openModal($(element).attr("target-modal"));
		})
	}

	function initializeModalCloseTrigger(index, element) {
		$(element).click(function(){
			closeModal($(element).attr("target-modal"));
		})
	}

	function openModal(modalId) {
		$("#"+modalId).click(function(event) {
			if(event.target===$("#"+modalId)[0]) {
				closeModal(modalId);
			}
		});
		$("#"+modalId).css({
			"display":"block"
		});
		$("#"+modalId+" > .body").css({
			"display":"block",
			"margin-top":"-100%"
		});
		$("#"+modalId+" > .body").animate({
			"margin-top":"5%"
		});
	}

	function closeModal(modalId) {
		$("#"+modalId+" > .body").animate({
			"margin-top":"-100%"
		}, function() {
			$("#"+modalId).css({
				"display":"none"
			});
			$("#"+modalId+" > .body").css({
				"display":"none"
			});
		});
	}