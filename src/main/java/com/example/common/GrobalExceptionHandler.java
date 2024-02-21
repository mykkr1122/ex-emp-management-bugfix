package com.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GrobalExceptionHandler implements HandlerExceptionResolver{
	private static final Logger LOGGER
			= LoggerFactory.getLogger(GrobalExceptionHandler.class);

    @Override
	public ModelAndView resolveException(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object obj, 
			Exception e) {
		LOGGER.error("システムエラーが発生しました！", e);
		return null; 
    }
}
