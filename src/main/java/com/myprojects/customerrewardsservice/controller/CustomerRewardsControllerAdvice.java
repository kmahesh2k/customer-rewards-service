package com.myprojects.customerrewardsservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myprojects.customerrewardsservice.exception.BadRequestException;
import com.myprojects.customerrewardsservice.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomerRewardsControllerAdvice {

	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleAppExceptions(HttpServletRequest req, Throwable ex) {

		ErrorResponse errorResponse;

		if (ex instanceof BadRequestException) {
			log.error("Bad Request", ex);
			BadRequestException badRequestException = (BadRequestException) ex;
			errorResponse = new ErrorResponse(req.getRequestURI(), badRequestException.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else {
			log.error("General error", ex);
			errorResponse = new ErrorResponse(req.getRequestURI(), ex.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
