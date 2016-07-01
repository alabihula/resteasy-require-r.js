require.config({
    paths: {
        jquery: '../../commons/js/jquery-1.12.4.min',
        jqueryUI:'jquery-ui.min'
    }
});
 
require(['jquery','jqueryUI'],function($) {
	$('#tabs').tabs(
			{
//				active:1,//默认显示的标签
				activate :function(e,ui) {
					console.log(e);
					console.log(ui);
				}
			}
		).show();
	
//	$( "#tabs" ).tabs( "option", "active", 2 );
	
	
	
	
	
	
	
	
	
	
	
	
});


