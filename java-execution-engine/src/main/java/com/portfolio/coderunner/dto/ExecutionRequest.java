package com.portfolio.coderunner.dto;

import lombok.Data;

@Data
public class ExecutionRequest {
    private String language;
    private String code;
}