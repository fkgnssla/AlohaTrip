package com.ssafy.exception;

import com.ssafy.dto.error.ResponseErrorDto;
import com.ssafy.exception.member.MemberNotFoundException;
import com.ssafy.exception.plan.PlanNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> memberNotFound(MemberNotFoundException e) {
        ResponseErrorDto responseErrorDto = ResponseErrorDto.builder()
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorDto);
    }

    @ResponseBody
    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> planNotFound(PlanNotFoundException e) {
        ResponseErrorDto responseErrorDto = ResponseErrorDto.builder()
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorDto);
    }

}
