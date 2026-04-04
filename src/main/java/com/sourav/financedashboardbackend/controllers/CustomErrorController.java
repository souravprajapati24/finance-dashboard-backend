package com.sourav.financedashboardbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Integer statusAttr = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        int status = (statusAttr != null) ? statusAttr : 500;
        String path = (String) request.getAttribute("jakarta.servlet.error.request_uri");

        return ResponseEntity.status(status)
                .body(Map.of(
                        "message", status == 404 ? "API Not Found" : "Internal Server Error",
                        "path", path));
    }
}
