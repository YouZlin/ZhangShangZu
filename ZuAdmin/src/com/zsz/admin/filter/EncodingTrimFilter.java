package com.zsz.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.zsz.tools.TrimHttpRequest;

@WebFilter("/*")
public class EncodingTrimFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//解决编码格式问题
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		chain.doFilter(request, response);//一定加上，否则无法继续跳转

		//进行去空格处理
		TrimHttpRequest trimReq=new TrimHttpRequest((HttpServletRequest)request);
		chain.doFilter(trimReq, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
