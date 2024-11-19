package com.wpits.towardsErp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RestControllerAdvice
public class CustomResponseBody{
	private HttpStatus httpCode;
	private Object data;
	private CustomResponseEnum status;
	private String message;
	private String latencyTime;
	
	public int getHttpCode() {
		if(httpCode!=null)
		return httpCode.value();
		else
			return 500;
	}

	public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public  String getTimestamp() {
		return sdf.format(new Date());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomResponseBody> handleInvalidArgument(MethodArgumentNotValidException ex) {
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();	
		System.out.println("here here here here here");
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			errors.put(error.getField(), error.getDefaultMessage());
		});
		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		//return errors;
		return new	ResponseEntity<CustomResponseBody>(new CustomResponseBody(HttpStatus.BAD_REQUEST, errors, CustomResponseEnum.FAILED, CustomResponsePredefinedMessage.BadRequest, l_diff+" ms"),HttpStatus.BAD_REQUEST);
	}
	
	
	
	

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<CustomResponseBody> handleNoSuchElement(NoSuchElementException ex) {
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return new	ResponseEntity<CustomResponseBody>(new CustomResponseBody(HttpStatus.NOT_FOUND, ex.getMessage(), CustomResponseEnum.FAILED, CustomResponsePredefinedMessage.ResourceNotFound, l_diff+" ms"),HttpStatus.NOT_FOUND);
	}

}