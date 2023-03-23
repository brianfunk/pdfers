package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.HtmlToPdfService;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HtmlToPdfController {

    private final HtmlToPdfService htmlToPdfService;

    public HtmlToPdfController(HtmlToPdfService htmlToPdfService) {
        this.htmlToPdfService = htmlToPdfService;
    }

    @PostMapping(value = "/generate-pdf-from-url", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdfFromUrl(@RequestBody String url) throws IOException {
        byte[] pdfContent = htmlToPdfService.generatePdfFromUrl(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "inline; filename=output.pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/generate-pdf-from-html", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdfFromHtml(@RequestBody String html) throws IOException {
        byte[] pdfContent = htmlToPdfService.generatePdfFromHtml(html);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "inline; filename=output.pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
