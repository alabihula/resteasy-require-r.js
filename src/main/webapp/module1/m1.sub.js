

//define(id?, dependencies?, factory); // ? 表示可选项
//define({
//	getInfo:function(name) {
//		return "我的名字是："+name;
//	}
//})

define(function() {
	var k ={};
	var alert = {};
	k.getInfo = function(name) {
			return "我的名字是："+name+" alert模块信息： "+this.alert.aaa;
	}
	k.init = function(alert) {
		this.alert = alert;
	}
    return k;
    
})