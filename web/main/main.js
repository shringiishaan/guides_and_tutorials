
	var applicationState = {
		template:"",
		sideBar:{
			collapse:""
		}
	};

	/**
	 *	template ::
	 *
	 *			small (<800) : sidebar hidden, infobar bottom
	 *
	 *			medium (>=800  <1000) : sidebar inpage, infobar bottom
	 *
	 *			large (>=1000) : sidebar inpage, infobar inpage
	 */

	$(function(){
		initializeApplication();
	});

	function initializeApplication () {

		if(window.innerWidth<800) {
			applicationState.template = "small";
		}
		else if(window.innerWidth<1000) {
			applicationState.template = "medium";
		}
		else {
			applicationState.template = "large";
		}

		sidebar_initialize();
		infobar_initialize();
		modal_initialize();
		dropdown_initialize();
	}