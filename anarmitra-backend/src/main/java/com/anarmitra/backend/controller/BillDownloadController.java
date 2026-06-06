package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.Bill;
import com.anarmitra.backend.entity.BillItem;
import com.anarmitra.backend.repository.BillRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/bills")
public class BillDownloadController {

    private final BillRepository billRepository;

    public BillDownloadController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadBill(@PathVariable Long id) {
        try {
            Bill bill = billRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bill not found"));

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(27, 94, 32));
            Font headingFont = new Font(Font.HELVETICA, 13, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL);

            Paragraph title = new Paragraph("AnarMitra - Farmer Bill", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            document.add(new Paragraph("Bill ID: " + bill.getId(), normalFont));
            document.add(new Paragraph("Farmer ID: " + bill.getFarmerId(), normalFont));
            document.add(new Paragraph("Seller ID: " + bill.getSellerId(), normalFont));
            document.add(new Paragraph("Seller Type: " + bill.getSellerType(), normalFont));
            document.add(new Paragraph("Date: " + bill.getCreatedAt(), normalFont));
            document.add(new Paragraph(" "));

            Paragraph itemHeading = new Paragraph("Bill Items", headingFont);
            itemHeading.setSpacingBefore(10);
            itemHeading.setSpacingAfter(10);
            document.add(itemHeading);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4, 1.5f, 2, 2});

            addHeaderCell(table, "Item Name");
            addHeaderCell(table, "Qty");
            addHeaderCell(table, "Price");
            addHeaderCell(table, "Total");

            if (bill.getItems() != null) {
                for (BillItem item : bill.getItems()) {
                    table.addCell(new Phrase(item.getItemName(), normalFont));
                    table.addCell(new Phrase(String.valueOf(item.getQuantity()), normalFont));
                    table.addCell(new Phrase("Rs. " + item.getPrice(), normalFont));
                    table.addCell(new Phrase("Rs. " + item.getTotal(), normalFont));
                }
            }

            document.add(table);

            Paragraph total = new Paragraph("Total Amount: Rs. " + bill.getTotalAmount(), headingFont);
            total.setAlignment(Paragraph.ALIGN_RIGHT);
            total.setSpacingBefore(20);
            document.add(total);

            Paragraph footer = new Paragraph(
                    "Thank you for using AnarMitra. This bill is generated digitally.",
                    normalFont
            );
            footer.setSpacingBefore(30);
            footer.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(footer);

            document.close();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=bill_" + id + ".pdf"
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private void addHeaderCell(PdfPTable table, String text) {
        Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);

        PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
        cell.setBackgroundColor(new Color(46, 125, 50));
        cell.setPadding(8);

        table.addCell(cell);
    }
}