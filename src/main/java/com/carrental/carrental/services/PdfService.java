package com.carrental.carrental.services;

import com.carrental.carrental.entities.Location;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import java.io.*;

@Service
public class PdfService {
    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public File createPdfHtml(String imageUrl, String carName, Location locationName, String fromDate, String toDate, String name, Integer price) throws IOException, DocumentException {
        Context context = new Context();
        context.setVariable("imageUrl",imageUrl);
        context.setVariable("carName",carName);
        context.setVariable("price","$"+price);
        context.setVariable("longitude",locationName.getLongitude());
        context.setVariable("latitude",locationName.getLatitude());
        context.setVariable("locationName",locationName.getLocationName());
        context.setVariable("fromDate",fromDate);
        context.setVariable("toDate",toDate);
        String processHtml = templateEngine.process("pdfhtml",context);
        return returnPdf(processHtml,name);
    }
    public File returnPdf(String processHtml, String name) throws IOException, DocumentException{
        File pdfFile = new File("invoice.pdf");
        OutputStream outputStream = new FileOutputStream(pdfFile);
        PDFEncryption pdfEncryption = new PDFEncryption();
        pdfEncryption.setUserPassword(name.getBytes());
        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.setPDFEncryption(pdfEncryption);
        iTextRenderer.setDocumentFromString(processHtml);
        iTextRenderer.layout();
        iTextRenderer.createPDF(outputStream);
        iTextRenderer.finishPDF();
        outputStream.close();
        return pdfFile;
    }
}