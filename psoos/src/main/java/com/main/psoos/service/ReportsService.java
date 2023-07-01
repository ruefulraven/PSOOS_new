package com.main.psoos.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.main.psoos.model.Order;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ReportsService {

    @Autowired
    private CustomerService customerService;


    public void printOrders(List<Order> order, Writer writer){
        try{
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord("Job ID",
                    "Customer Name",
                    "Total Price",
                    "Job Order Created",
                    "Job Order Finish",
                    "Status",
                    "Worker");
            for(Order tempOrder : order){
                String customerName = customerService
                        .getCustomerById(tempOrder.getCustomerId())
                        .getCustomerName();

                printer.printRecord(tempOrder.getJoId(),
                        customerName,
                        tempOrder.getTotalPrice(),
                        tempOrder.getCreationDate(),
                        tempOrder.getFinishDate(),
                        tempOrder.getStatus(),
                        tempOrder.getWorker());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printOrdersPdf(List<Order> order, HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        Paragraph paragraph1 = new Paragraph("Job Orders List", fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);
        PdfPTable table = new PdfPTable(6);
        // Setting width of the table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] {3,3,3,3,3,3});
        table.setSpacingBefore(5);
        // Create Table Cells for the table header
        PdfPCell cell = new PdfPCell();
        // Setting the background color and padding of the table cell
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);
        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);
        // Adding headings in the created table cell or  header
        // Adding Cell to table
        cell.setPhrase(new Phrase("Customer Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Created", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Finish", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Price", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Worker", font));
        table.addCell(cell);
        for(Order tempOrder : order){
            String customerName = customerService
                    .getCustomerById(tempOrder.getCustomerId())
                    .getCustomerName();

            table.addCell(customerName);
            table.addCell(tempOrder.getTotalPrice().toString());
            table.addCell(tempOrder.getFinishDate());
            table.addCell(tempOrder.getCreationDate().toString());
            table.addCell(tempOrder.getStatus());
            table.addCell(tempOrder.getWorker());
        }
        // Adding the created table to the document
        document.add(table);
        // Closing
        document.close();
    }
}
