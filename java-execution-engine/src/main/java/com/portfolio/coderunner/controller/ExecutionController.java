package com.portfolio.coderunner.controller;

import com.portfolio.coderunner.dto.ExecutionRequest;
import com.portfolio.coderunner.dto.ExecutionResponse;
import com.portfolio.coderunner.service.ExecutionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("/execute")
    public ExecutionResponse executeCode(@RequestBody ExecutionRequest request) {
        return executionService.execute(request.getCode());
    }
}