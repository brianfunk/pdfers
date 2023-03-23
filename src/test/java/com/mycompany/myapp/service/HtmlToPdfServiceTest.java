package com.mycompany.myapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HtmlToPdfServiceTest {

    @InjectMocks
    private HtmlToPdfService htmlToPdfService;

    @Mock
    private Playwright playwright;

    @Mock
    private BrowserType browserType;

    @Mock
    private Browser browser;

    @Mock
    private Page page;

    @BeforeEach
    public void setUp() {
        when(playwright.chromium()).thenReturn(browserType);
        when(browserType.launch(any())).thenReturn(browser);
        when(browser.newPage()).thenReturn(page);
    }

    @Test
    public void testGeneratePdfFromUrl() throws Exception {
        String url = "https://example.com";
        byte[] pdfContent = "PDF_CONTENT".getBytes();

        when(page.pdf()).thenReturn(pdfContent);

        byte[] result = htmlToPdfService.generatePdfFromUrl(url);

        assertThat(result).isEqualTo(pdfContent);
        verify(page, times(1)).navigate(url);
        verify(page, times(1)).pdf();
    }

    @Test
    public void testGeneratePdfFromHtml() throws Exception {
        String html = "<html><body>Hello World</body></html>";
        byte[] pdfContent = "PDF_CONTENT".getBytes();

        when(page.pdf()).thenReturn(pdfContent);

        byte[] result = htmlToPdfService.generatePdfFromHtml(html);

        assertThat(result).isEqualTo(pdfContent);
        verify(page, times(1)).setContent(html);
        verify(page, times(1)).pdf();
    }
}
