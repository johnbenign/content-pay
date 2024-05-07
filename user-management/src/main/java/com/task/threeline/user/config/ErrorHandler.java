package com.task.threeline.user.config;

import com.task.threeline.user.dto.GeneralResponse;
import com.task.threeline.user.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

	@ExceptionHandler(value = { NotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public GeneralResponse misMatchErrorHandler(NotFoundException ex) {
		log.info("throwing this::::::::::::: {}", ex.getMessage());
		return new GeneralResponse(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { ConstraintException.class })
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
	public GeneralResponse handleConstraintError(ConstraintException ex) {
		log.info("throwing this::::::::::::: {}", ex.getMessage());
		return new GeneralResponse(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { ForbiddenException.class })
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public GeneralResponse forbiddenErrorHandler(ForbiddenException ex) {
		return new GeneralResponse(HttpStatus.FORBIDDEN.value(),
				ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { UnauthorizedException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public GeneralResponse unauthorizedErrorHandler(UnauthorizedException ex) {
		return new GeneralResponse(HttpStatus.UNAUTHORIZED.value(),
				ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex) {
		log.info("Method Argument not valid throwing....");

		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage())
				.collect(Collectors.toList());

		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(), errorList.get(0));

	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse illegalArgumentExceptionHandler(IllegalArgumentException ex) {
		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { BadRequestException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse badRequestExceptionHandler(BadRequestException ex) {
		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { BadCredentialException.class })
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public GeneralResponse badCredentialExceptionHandler(BadCredentialException ex) {
		return new GeneralResponse(HttpStatus.NOT_ACCEPTABLE.value(),
				ex.getLocalizedMessage());
	}
}
