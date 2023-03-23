package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.HtmlToPdfService;
import java.io.IOException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HtmlToPdfController {

    private final HtmlToPdfService htmlToPdfService;

    public HtmlToPdfController(HtmlToPdfService htmlToPdfService) {
        this.htmlToPdfService = htmlToPdfService;
    }

    private boolean isValidFilename(String filename) {
        String regex = "^[\\w\\-\\.\\s]+$";
        return filename.matches(regex);
    }

    @PostMapping("/api/generate-pdf-from-url")
    public ResponseEntity<byte[]> generatePdfFromUrl(
        @RequestBody String url,
        @RequestParam(value = "filename", defaultValue = "download") String filename
    ) throws Exception {
        if (!isValidFilename(filename)) {
            throw new IllegalArgumentException("Invalid filename");
        }

        byte[] pdfContent = htmlToPdfService.generatePdfFromUrl(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename + ".pdf").build());
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping("/api/generate-pdf-from-html")
    public ResponseEntity<byte[]> generatePdfFromHtml(
        @RequestBody String html,
        @RequestParam(value = "filename", defaultValue = "download") String filename
    ) throws Exception {
        if (!isValidFilename(filename)) {
            throw new IllegalArgumentException("Invalid filename");
        }

        byte[] pdfContent = htmlToPdfService.generatePdfFromHtml(html);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename + ".pdf").build());
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
