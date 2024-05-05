package com.greatlearning.tickettracker.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String actionName = "";

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			actionName = handlerMethod.getMethod().getName();

			if (StringUtils.hasText(actionName)) {
				log.info("Initiating Api Call for Service '" + actionName + "'");
			}
		} else if (handler instanceof ParameterizableViewController) {
			ParameterizableViewController controller = (ParameterizableViewController) handler;
			log.info("Initiating View Call for '" + controller.getViewName() + "'");
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String actionName = "";

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			actionName = handlerMethod.getMethod().getName();

			if (StringUtils.hasText(actionName)) {
				log.info("Completed View Call for '" + actionName + "' , Loading View Now");
			}
		} else if (handler instanceof ParameterizableViewController) {
			ParameterizableViewController controller = (ParameterizableViewController) handler;
			log.info("Completed View Call for '" + controller.getViewName() + "' , Loading View Now");
		}

	}
}
