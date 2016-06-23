	var ob = ob || {};
	ob.test = function() {
		console.log('returnnormal js');
	}
	
	
	// Expose jQuery to the global object
//	window.ob = window.T = ob;
	if ( typeof define === "function" && define.amd ) {
		define( "returnNormal", [], function () { return ob; } );
	}
