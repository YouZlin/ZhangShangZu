package com.zsz.admin.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zsz.admin.utils.AdminUtils;
import com.zsz.tools.AjaxResult;

public class BaseServlet extends HttpServlet {
	private static final Logger logger=LogManager.getLogger(BaseServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action=req.getParameter("action");
		if (StringUtils.isEmpty(action)) {
			AdminUtils.showError(req, resp, "action is empty!");
			logger.warn("action is empty");
			return;
		}
		Class childClz=this.getClass();//获得的是子类的class,不是当前的BaseServlet
		try {
			//方法名应与传入的action相一致
			Method methodAction=childClz.getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
			//methodAction获得action的方法,传入的参数有action,HttpServletRequest,HttpServletResponse
				methodAction.invoke(this, req,resp);
			
		} catch (NoSuchMethodException | SecurityException e) {
			AdminUtils.showError(req, resp, "cannot invoke action method:"+action);
			logger.warn("cannot invoke action method:"+action,e);
		}catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			AdminUtils.showError(req, resp, "invoke method "+action+" error");
			logger.warn("invoke method "+action+" error",e);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	protected void writeJson(HttpServletResponse response,AjaxResult ajaxResult) throws IOException{
		response.setContentType("application/json");
		response.getWriter().print(ajaxResult.toString());
		
	}

}
