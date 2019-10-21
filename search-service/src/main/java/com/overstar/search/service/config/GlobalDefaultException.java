package com.overstar.search.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalDefaultException {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String defaultExceptionHandler(Exception e) {
		log.error("GlobalDefaultException", e);
		return String.format("未知异常：%s", e.getMessage());
	}

}
