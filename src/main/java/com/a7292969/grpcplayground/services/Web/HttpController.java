package com.a7292969.grpcplayground.services.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {
    @Autowired
    private ProcessingClient firstService;

    @GetMapping("/")
    public ResponseEntity<byte[]> root(@RequestParam(value = "text", defaultValue = "Golden Bread") String text) {
        byte[] pngData = firstService.drawText(text);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(pngData, headers, HttpStatus.OK);
    }
}
