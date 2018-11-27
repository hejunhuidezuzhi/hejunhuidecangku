package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.Backenduser;
import cn.appsys.pojo.Devuser;

public class SysInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		HttpSession session = request.getSession();
		
		Backenduser backendUser = (Backenduser)session.getAttribute("userSession");
		Devuser devUser = (Devuser)session.getAttribute("devUserSession");
		
		if(null != devUser){ 
			return true;
		}else if(null != backendUser){ 
			return true;
		}else{
			response.sendRedirect(request.getContextPath()+"/403.jsp");
			return false;
		}
		
	}
}
