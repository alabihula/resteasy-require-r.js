package com.server.rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.RequestWrapper;

import org.apache.http.protocol.HttpContext;
import org.jboss.resteasy.annotations.ResponseObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(value = "/getForm")
	public String getForm(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response)
	{
		String phone = request.getParameter("phone");
		InputStream in = null;
		BufferedReader br = null;
		try {
			in = request.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String st = br.readLine();//这时接收的便是josn格式的字符串
			JSONObject   ob = JSONObject.parseObject(st);//将json字符串转换为对象，然后可以通过key来取值了
			System.out.println(ob);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ResponseResult resultStr = new ResponseResult();
		resultStr.setCode(0);
		resultStr.setMsg("调用成功");
		resultStr.setValue("传入参数phone："+phone);
		return JSON.toJSONString(resultStr);
	}
	
	@POST
	@Path(value = "/upload")
	public String upload(
			@Context HttpServletRequest request,
			@Context HttpContext context,
			@Context HttpServletResponse response)
	{
		ResponseResult resultStr = new ResponseResult();
		resultStr.setCode(0);
		resultStr.setMsg("调用成功");
		resultStr.setValue("传入参数phone："+1);
		JSONObject jsonData = new JSONObject();
		jsonData.put("fileName", "hahha.png");
		return JSON.toJSONString(jsonData);
	}
	
	@GET
	@Path(value = "/check")
	public String checkCode(
            @QueryParam("code") int code,
            @QueryParam("code1") int code1,
            @QueryParam("code2") int code2,
            @QueryParam("code3") int code3
            )
	{
		
		ResponseResult resultStr = new ResponseResult();
		resultStr.setMsg("调用成功");
		resultStr.setValue("传入参数："+code);
		System.out.println("code: "+code);
		System.out.println("code1: "+code1);
		System.out.println("code2: "+code2);
		System.out.println("code3: "+code3);
		return JSON.toJSONString(resultStr);
	}
	
	@GET
	@Path(value = "/getDetail")
	@Produces("application/json; charset=utf-8")  
	public String detail(
			@QueryParam("pageSize") int pageSize,
			@QueryParam("pageNumber") int pageNumber,
			@QueryParam("pageStyle") int pageStyle
			)
	{
		System.out.println("pageSize: "+pageSize);
		System.out.println("pageStyle: "+pageStyle);
		System.out.println("pageNumber: "+pageNumber);
		
		
		JSONObject returnJson = new JSONObject();
		JSONArray jsonData=new JSONArray();
        JSONObject jo=null;
        for (int i=0;i<150;i++)
        {
            jo=new JSONObject();
            jo.put("id",  i+1);
            jo.put("liushuiid", "liushui "+i);
            jo.put("price", String.format("%1.2f",(i+50)/100.0));
            jo.put("goodroadid", "goodsid "+i);
            jo.put("goodsName", "name: "+i);
            jsonData.add(jo);
        }
        
        //判断是否需要服务端分页，0服务端，1客户端
  		if(pageStyle == 0) {//服务端
  			if(pageNumber == 0) pageNumber = 1;
  			if(pageSize == 0) pageSize = 10;
  			JSONArray jsonData2 = new JSONArray();
  			JSONObject jo2 = null;
  			for (int i = 0; i < pageSize; i++) {
  				jo2=new JSONObject();
  				jo2.put("id",  i+1+" 页码："+pageNumber);
  				jo2.put("liushuiid", "liushui "+i);
  				jo2.put("price", String.format("%1.2f",(i+50)/100.0));
  				jo2.put("goodroadid", "goodsid "+i);
  				jo2.put("goodsName", "name: "+i);
  				jsonData2.add(jo2);
			}
  			returnJson.put("rows", jsonData2);//JSONArray
  			returnJson.put("total",20);//总记录数
  			return JSON.toJSONString(returnJson);
  		}
		return JSON.toJSONString(jsonData);
	}
	
	public static void main(String[] args) {
		String ab = "{aaa:1}";
		JSONObject obj = JSON.parseObject(ab);
		System.out.println(obj.getString("aaa"));
	}
}
