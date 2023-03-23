package com.mycompany.myapp.service;

import com.microsoft.playwright.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class HtmlToPdfService {

    public byte[] generatePdfFromUrl(String url) throws IOException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate(url);
            byte[] pdfContent = page.pdf(new Page.PdfOptions().setFormat("A4").setPrintBackground(true));

            browser.close();
            return pdfContent;
        }
    }

    public byte[] generatePdfFromHtml(String html) throws IOException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.setContent(html);
            byte[] pdfContent = page.pdf(new Page.PdfOptions().setFormat("A4").setPrintBackground(true));

            browser.close();
            return pdfContent;
        }
    }
}
