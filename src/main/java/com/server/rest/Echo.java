package com.server.rest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.server.util.SecurityCode;
import com.server.util.SecurityImage;
import com.server.vo.ResponseResult;

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
