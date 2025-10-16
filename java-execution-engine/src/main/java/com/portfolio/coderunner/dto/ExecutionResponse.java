package com.portfolio.coderunner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResponse {
    private String output;
    private String error;
    private boolean isSuccess;
}