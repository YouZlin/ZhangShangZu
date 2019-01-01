package com.zsz.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.zsz.tools.AjaxResult;

@WebServlet("/Index")
public class IndexServlet extends BaseServlet {

	/*
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action=req.getParameter("action");
		if (StringUtils.isEmpty(action)||action.equals("index")) {
			index(req, resp);
		}else if (action.equals("login")) {
			login(req, resp);
		}else if (action.equals("loginSubmit")) {
			loginSubmit(req, resp);
		}
		
	}
	*/
	public void index(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
		
	}
	public void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
	public void loginSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		writeJson(resp, new AjaxResult("ok", "删除成功", 1));
	}
	
}
