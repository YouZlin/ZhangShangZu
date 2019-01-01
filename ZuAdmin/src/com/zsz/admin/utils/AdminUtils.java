package com.zsz.admin.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminUtils {
	
	public static void showError(HttpServletRequest req,HttpServletResponse resp,String errorMsg) throws ServletException, IOException{
		req.setAttribute("errorMsg", errorMsg);
		req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
	}

}
