package com.interview.prototype.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.interview.prototype.util.ErrorInfo;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody ErrorInfo
	handleMethodArgumentNotValidExceptions(HttpServletRequest req, MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
		});
		
		return new ErrorInfo(req, errors);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody ErrorInfo
	handleValidationExceptions(HttpServletRequest req, ConstraintViolationException ex) {
		List<String> errors = new ArrayList<>();
		ex.getConstraintViolations().forEach((violations) -> {
			String errorMessage = violations.getMessage();
			errors.add(errorMessage);
		});
		
		return new ErrorInfo(req, errors);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody ErrorInfo
	handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException ex) {
		return new ErrorInfo(req, ex);
	}
		
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody ErrorInfo
	handleApplicationExceptionException(HttpServletRequest req, ApplicationException ex) {
		return new ErrorInfo(req, ex);
	}	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody ErrorInfo
	handleAllOtherExceptions(HttpServletRequest req, Exception ex) {
		return new ErrorInfo(req, ex);
	}
}
