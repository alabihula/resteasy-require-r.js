package com.server.rest;
 import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

 
 public class FilterEcho implements Filter {
     private String encoding;
 
     public FilterEcho() {
     }
 
     public void init(FilterConfig filterconfig) throws ServletException {
         encoding = "utf-8";
     }
 
     public void doFilter(ServletRequest servletrequest,
             ServletResponse servletresponse, FilterChain filterchain)
             throws IOException, ServletException {
         servletrequest.setCharacterEncoding(encoding);
         servletresponse.setCharacterEncoding(encoding);
         HttpServletRequest request=(HttpServletRequest)servletrequest;
         HttpServletResponse response=(HttpServletResponse) servletresponse;
         response.setHeader("Access-Control-Allow-Origin","*");
         response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
         response.setHeader("Access-Control-Max-Age","3600");
         response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
         filterchain.doFilter(request, response);
     }
 
     public void destroy() {
     }
     public static void main(String[] args) {
		String str = "";
		if(StringUtils.isEmpty(str)) {
       	 System.out.println("empty");
        }
	}
 }