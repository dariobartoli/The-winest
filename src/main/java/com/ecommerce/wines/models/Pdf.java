package com.ecommerce.wines.models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Pdf {
    Document document;

    Font titleSource = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16);

    Font paragraphSource = FontFactory.getFont(FontFactory.HELVETICA, 12);

    public void createDocument(HttpServletResponse response) throws IOException, DocumentException {
        document = new Document(PageSize.A4, 35, 30, 50, 50);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
    }

    public void addTitle(String text) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase(text, titleSource));
        cell.setColspan(5);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        Image image = Image.getInstance("https://media.discordapp.net/attachments/1017325567630118972/1047836625725571072/Modern_Black__Red_Luxury_Wine_Tasting_Instagram_Post.png?width=520&height=120");
        document.add(image);
        Paragraph lineJumps = new Paragraph();
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        document.add(lineJumps);
        document.add(table);

    }

    public void addParagraph(String text) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(text, paragraphSource));
        document.add(paragraph);
    }

    public void addLineJumps() throws DocumentException {
        Paragraph lineJumps = new Paragraph();
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        document.add(lineJumps);
    }

    public void addPurchaseOrderTable(PurchaseOrder purchaseOrder, Client client) throws DocumentException {

        PdfPTable table = new PdfPTable(4);
        table.addCell("Order Nº");
        table.addCell("Client");
        table.addCell("Date");
        table.addCell("Payment Method");
        table.addCell(purchaseOrder.getId() + "");
        table.addCell(client.getFirstName() + " " + client.getLastName());
        table.addCell(purchaseOrder.getLocalDateTime().toString());
        table.addCell(purchaseOrder.getPaymentMethod().toString());
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        document.add(table);
    }

    public void addProductsTable(List<ProductOrder> productOrders) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.addCell("Products");
        table.addCell("Quantity");
        table.addCell("Amount");
        productOrders.stream().sorted(Comparator.comparing(ProductOrder::getId)).forEach(productOrder -> {
            table.addCell(productOrder.getProduct().getName());
            table.addCell(productOrder.getQuantity().toString());
            table.addCell("$ " + productOrder.getAmount());
                });
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        document.add(table);
    }

    public void closeDocument() {
        document.close();
    }
}
