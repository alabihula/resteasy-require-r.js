require.config({
    paths: {
        jquery: '../commons/js/jquery-1.12.4.min'
    },
    map:{
        'useName1': {
            'name': 'name_v1'
        },
        'useName2': {
            'name': 'name_v2'
        }
    }
});
 
require(['useName1','useName2'],function(use1,use2) {
	console.log(use1.do+"---"+use2.do);
});


