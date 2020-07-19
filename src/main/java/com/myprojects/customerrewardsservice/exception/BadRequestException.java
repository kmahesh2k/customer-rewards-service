package com.myprojects.customerrewardsservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
}
