package com.retail.readingisgood.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseExceptionResponse {

    private String errorMessage;
    private String errorType;
    private List<String> details;
    private Date timestamp;

}
