package com.nts.reservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class})
	public void illegalArgumentHandler(IllegalArgumentException exception) {
		LOGGER.error(exception.getMessage(), exception);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({NumberFormatException.class})
	public void numberFormatHandler(NumberFormatException exception) {
		LOGGER.error("uri is not integer", exception);
	}

}
