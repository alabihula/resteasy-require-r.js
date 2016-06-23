define(['jquery','m2Sub'],function($,sub) {
	var alert = {};
	alert.aaa = "我来自模块alert";
	
	//为需要依赖此js的sub模块赋值
	sub.init(alert);
	function showMsg(str) {
		$('.msg_box').css({
			'margin':'50px auto',
			'width':"800px",
			'height':'50px',
			'font-size':'20px',
			'text-align':'center',
			'line-height':'50px',
			'box-shadow':'0px 0px 10px 2px',
			'display':'none'
		}).html(str+"-"+sub.getInfo("闵星")).show();
	}
	
	$('.box').on('click',function() {
		showMsg("测试弹框-");
	})
	
	return alert;
})
