package com.a7292969.grpcplayground.services.web;

import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HttpController {
    private final ProcessingClient  firstService;

    public HttpController(ProcessingClient firstService) {
        this.firstService = firstService;
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> root(@RequestParam(value = "text", defaultValue = "Golden Bread") String text) {
        byte[] pngData = firstService.drawText(text);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(pngData, headers, HttpStatus.OK);
    }

    @ExceptionHandler(StatusRuntimeException.class)
    public String handleError(HttpServletRequest req, StatusRuntimeException e) {
        return "ERROR: " + e.getMessage();
    }
}
