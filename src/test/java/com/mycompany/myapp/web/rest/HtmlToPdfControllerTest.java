package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.service.HtmlToPdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class HtmlToPdfControllerTest {

    @InjectMocks
    private HtmlToPdfController htmlToPdfController;

    @Mock
    private HtmlToPdfService htmlToPdfService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(htmlToPdfController, "htmlToPdfService", htmlToPdfService);
    }

    @Test
    public void testGeneratePdfFromUrl() throws Exception {
        byte[] pdfContent = "PDF_CONTENT".getBytes();
        String url = "https://example.com";
        String fileName = "testFile";

        when(htmlToPdfService.generatePdfFromUrl(any(String.class))).thenReturn(pdfContent);

        ResponseEntity<byte[]> response = htmlToPdfController.generatePdfFromUrl(url, fileName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pdfContent);
        assertThat(response.getHeaders().getContentType()).isEqualTo(org.springframework.http.MediaType.APPLICATION_PDF);
        assertThat(response.getHeaders().getContentDisposition().getFilename()).isEqualTo(fileName + ".pdf");

        verify(htmlToPdfService, times(1)).generatePdfFromUrl(url);
    }

    @Test
    public void testGeneratePdfFromHtml() throws Exception {
        byte[] pdfContent = "PDF_CONTENT".getBytes();
        String html = "<html><body>Hello World</body></html>";
        String fileName = "testFile";

        when(htmlToPdfService.generatePdfFromHtml(any(String.class))).thenReturn(pdfContent);

        ResponseEntity<byte[]> response = htmlToPdfController.generatePdfFromHtml(html, fileName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pdfContent);
        assertThat(response.getHeaders().getContentType()).isEqualTo(org.springframework.http.MediaType.APPLICATION_PDF);
        assertThat(response.getHeaders().getContentDisposition().getFilename()).isEqualTo(fileName + ".pdf");

        verify(htmlToPdfService, times(1)).generatePdfFromHtml(html);
    }
}
