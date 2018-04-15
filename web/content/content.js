
	function renderContentForSideBar() {
		
		if(applicationState.template!=="small") {
			if(applicationState.sideBar.collapse==="true") {
				if(applicationState.template==="large") {
					$(".is-container").animate({
						"margin-left":"50px"
					});
				}
				else {
					$(".is-container").animate({
						"margin-left":"0px"
					});
				}
			}
			else {
				$(".is-container").animate({
					"margin-left":getSideBarWidth()+"px"
				});
			}
		}
	}