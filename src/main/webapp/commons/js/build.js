({
	//后续模块相对此路径
	baseUrl : "../../",
	//删除之前压缩合并的文件
	removeCombined:true,
	paths : {
		jquery : 'commons/js/jquery-1.12.4.min',
        m1Normal:'module1/m1.normal',
        m1Sub:'module1/m1.sub',
        m1Alert:'module1/m1.alert',
        m2Normal:'module2/m2.normal',
        m2Sub:'module2/m2.sub',
        m2Alert:'module2/m2.alert'
	},
	modules : [ 
	    {
	    	name : 'module1/main'
			// exclude:['jquery']
		},
		{
			name : 'module2/main'
			// exclude:['jquery']
		} 
	],
	
	// 指定输出目录，若值未指定，则相对 build 文件所在目录
	dir : '../../build'
//    name:"main",
//    out:"build/all.js"

})