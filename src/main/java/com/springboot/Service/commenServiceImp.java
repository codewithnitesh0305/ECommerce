package com.springboot.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class commenServiceImp implements commonService{

	@Override
	public void removeSessionMessage() {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute("successMsg");
		session.removeAttribute("errorMsg");
	}

	
}
