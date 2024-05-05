package com.greatlearning.tickettracker.exception;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.greatlearning.tickettracker.entity.Users;
import com.greatlearning.tickettracker.services.CustomUserDetailsService;
import com.greatlearning.tickettracker.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	Map<Integer, String> roleMap;

	private String role;

	private String userName;
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) {
		Users user = customUserDetailsService.geEffectiveUser();
		userName = user.getUsername();
		
		role = UserUtil.getHighestRole(user, roleMap);
		
		ModelAndView modelAndView = new ModelAndView();
		String errorCode = "404";
		if (exception instanceof HttpServerErrorException) {
			HttpServerErrorException ex = (HttpServerErrorException) exception;
			errorCode = ex.getStatusCode().value() + "";
		}
		modelAndView.addObject("exception", exception.getLocalizedMessage());
		modelAndView.addObject("url", request.getRequestURL());
		modelAndView.addObject("errorCode", errorCode);
		modelAndView.addObject("username", userName);
		modelAndView.addObject("role", role.toLowerCase());
		
		modelAndView.setViewName(DEFAULT_ERROR_VIEW);
		return modelAndView;
	}
}