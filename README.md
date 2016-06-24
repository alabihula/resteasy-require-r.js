# resteasy-require-r.js

#具体说下此工程主要用途吧
## 一 运用restful搭建了一个resteasy的web框架，并且运用解析后台接口代码最终渲染成js导出到前台，这个js就代表接口资源，包括了后台所有的接口调用，前端就可很方便的直接调用这个js对象里面的方法进行后台交互了

## 二 requireJs的模块化开发的例子以及具体的框架结构，并且包括运用构架工具r.js进行依赖压缩打包

###下面来详细说明下
###一 resteasy，首先上web.xml配置

<!-- 后台controller类地址 -->
  <context-param>
        <param-name>resteasy.resources</param-name>
        <param-value>com.server.rest.Echo</param-value>
    </context-param>
    
    <!-- 发布后访问的接口根目录名，例如后台接口为getName，访问： 工程名/rest/getName  -->
    <context-param>
        <param-name>resteasy.servlet.mapping.prefix</param-name>
        <param-value>/rest</param-value>
    </context-param>

    <listener>
        <listener-class>
            org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap</listener-class>
    </listener>
    
    
    <servlet>
        <servlet-name>resteasy-servlet</servlet-name>
        <servlet-class>
            org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher</servlet-class>
    </servlet>
    
         
    <servlet-mapping>
        <servlet-name>resteasy-servlet</servlet-name>
        <url-pattern>/rest/*</url-pattern>
    </servlet-mapping>

	<!-- 将后台接口导出到ajax js到前台 -->
	<servlet>
		<servlet-name>RESTEasy-JSAPI</servlet-name>
		<!--servlet-class>org.jboss.resteasy.jsapi.JSAPIServlet</servlet-class-->
		<servlet-class>com.server.rest.ConvertRestToJs</servlet-class>
	</servlet>

<!-- 最终导出到工程根目录的js名称，其中包括了后台所有接口封装好的对象  -->
	<servlet-mapping>
		<servlet-name>RESTEasy-JSAPI</servlet-name>
		<url-pattern>/restJs.js/*</url-pattern>
	</servlet-mapping>
	
	===
	###rest接口类
	@Path(value = "echo")
  public class Echo {
	@GET
	@Path(value = "/{message}")
	public String echoServicePath(@PathParam("message") String message)
	{
		return message;
	}
	
	@GET
	@Path(value = "/test")
	public String echoServiceQuery(
			@QueryParam("test") String test)
	{
		
		ResponseResult resultStr = new ResponseResult();
		resultStr.setCode(0);
		resultStr.setMsg("调用成功");
		resultStr.setValue("传入参数："+test);
		return JSON.toJSONString(resultStr);
	}
	
	@GET
	@Path(value = "/getPic")
	public String getPic(
			@Context HttpServletRequest request,
            @Context HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		String securityCode = SecurityCode.getSecurityCode();
		session.setAttribute("code", securityCode);
		String base64Str = "data:image/gif;base64,"+new SecurityImage().getImageAsInputStream(securityCode.replaceAll("", " "));
		return base64Str;
	}
	
	@GET
	@Path(value = "/check")
	public String checkCode(
			@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @QueryParam("code") String code)
	{
		HttpSession session = request.getSession();
		String SessionCode = (String) session.getAttribute("code");
		
		ResponseResult resultStr = new ResponseResult();
		resultStr.setMsg("调用成功");
		resultStr.setValue("传入参数："+code);;
		if(code.equals(SessionCode)) {
			resultStr.setCode(0);
		} else {
			resultStr.setCode(1);
		}
		return JSON.toJSONString(resultStr);
	}
	
	public static void main(String[] args) {
		String ab = "{aaa:1}";
		JSONObject obj = JSON.parseObject(ab);
		System.out.println(obj.getString("aaa"));
	}
}

###**不知道的可以详细去看下rest相关注解，其中封装了一个登录验证码的例子，我就一并放上来了-。-**

===
## 二requireJS

###首先来看下web目录结构,也是看了**张云龙**大神的web前端工程化，从而构建的目录结构
######------commons
######------------js
######---------------build.js
######---------------jquery.js
######---------------r.js
######---------------require.js
######------module1
######------------index.html
######------------m1.alert.js
######------------m1.normal.js
######------------m1.sub.js
######------------m1.main.js
######------module2
######------------index.html
######------------m2.alert.js
######------------m2.normal.js
######------------m2.sub.js
######------------m2.main.js
######------build
######------------commons
######------------module1
######------------module2
......

##然后通过nodejs 运用构建工具r.js 通过配置文件bulid.js对module1、module2进行依赖压缩打包，最终生成build目录（其中包括了依赖压缩后的main.js等,可以直接发布使用，这种方式没有考虑web静态文件优化（只是异步提高了页面加载，减少了http请求，达到了基本的模块化管理，升级部署还是覆盖式，这就导致了其他问题，具体的还是请去看看**张云龙博客中说的静态资源优化**），后续也会根据张云龙大神的讲解把自己的工程进行更好的优化），**使用命令：node r.js -o build.js**

###**其中有两个模块（alert和sub）我写成了相互依赖用来测试下怎么加载，如果同时require，那么谁在前谁在后都会报错，所以我想只要在a模块中定义一个b模块的初始化方法，然后在b模块中依赖a模块,同时把自身对象通过参数调用a模块的初始化方法，这样a模块中就有了b模块的实例，require的时候只需要加载b模块就可以了（不知道描述的清楚不），目前我也只是自己尝试了几种相互依赖的加载方法，如果有更好的还请大神指出~**

##build.js代码，其中也是试了好多种配置，最终认为可以的参数形式
######({
######	//后续模块相对此路径
######	baseUrl : "../../",
######	//删除每个模块的压缩文件，如果为false则保留压缩后的main中定义的每个模块
######	removeCombined:true,
######	paths : {
######		jquery : 'commons/js/jquery-1.12.4.min',
######        m1Normal:'module1/m1.normal',
######        m1Sub:'module1/m1.sub',
######        m1Alert:'module1/m1.alert',
######        m2Normal:'module2/m2.normal',
######        m2Sub:'module2/m2.sub',
######        m2Alert:'module2/m2.alert'
######	},
######	modules : [ 
######	    {
######	    	name : 'module1/main'
######			// exclude:['jquery']
######		},
######		{
######			name : 'module2/main'
######			// exclude:['jquery']
######		} 
######	],
	
######	// 指定输出目录，若值未指定，则相对 build 文件所在目录
######	dir : '../../build'
######//    name:"main",
######//    out:"build/all.js"
######
######})

##就到这吧，最近自己公司的项目也被我模块化整合了，后续优化也在不断进行中~ 第一次写这个。。。。不好的地方求放过
