package com.example.buensabor.Services.PdfService;

import com.example.buensabor.Services.PdfService.utils.FooterHelper;
import com.example.buensabor.Services.PdfService.utils.PdfTableUtility;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public class BillGenerator {

    private static final Rectangle DOCUMENT_RECT = new Rectangle(35, 55, 560, 780);
    private Document document;
    private List<String[]> billing_data;
    private String logo_fname = "";
    private String customer_email;
    private String customer_name;
    private String billing_id;
    private String discount;

    public enum LOGO_RESIZE_METHOD
    {
        Absolute,
        Percent
    }

    private String billing_header_title;

    private LOGO_RESIZE_METHOD logo_resize_method;
    private int logo_default_width;
    private int logo_default_height;
    private float logo_scaling_percent_value;

    /**
     * Create a new pdf billing.
     */
    public BillGenerator()
    {
        String output_fname = new File("").getAbsolutePath() + "/src/main/resources/Temp/" + "factura.pdf";
        billing_data = new ArrayList<>();
        logo_resize_method = LOGO_RESIZE_METHOD.Percent;
        logo_scaling_percent_value = 100;
        //billing_header_title = "Billing";

        try
        {
            document = new Document();
            document.setMargins(35f, 35f, 72f, 72f);  //A4

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(output_fname));

            writer.setBoxSize("billing_doc", DOCUMENT_RECT);                    //Set the document content rectangle area (A4)
            writer.setPageEvent(new FooterHelper());                      //Set the event handler for the pdf writer. This will write the footer at the end of every page
        } catch (FileNotFoundException | DocumentException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Populate the billing.
     */
    public ByteArrayOutputStream GenerateDocument() throws DocumentException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);

            document.open();

            document.add(createHeaderParagragh());
            document.add(Chunk.NEWLINE);
            document.add(createUserInformationParagraph());
            document.add(createBillingBodyParagraph());

            document.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return baos;
    }

    /**
     * Add a record to the billing
     *
     */
    public void AddBillingEntry(String article, String quantity, String price)
    {
        String subTotal = String.valueOf(Double.valueOf(quantity) * Double.valueOf(price));
        billing_data.add(new String[]
                {
                        article, quantity, price,subTotal
                });
    }

    /**
     * Set the customer name
     *
     */
    public void SetCustomerName(String id)
    {
        customer_name = id;
    }

    /**
     * Set the customer e-mail
     *
     */
    public void SetCustomerEmail(String email)
    {
        customer_email = email;
    }

    /**
     * Set the billing identifier
     *
     */
    public void SetBillingIdentifier(String billing_id)
    {
        this.billing_id = billing_id;
    }

    public void SetDiscount(String discount)
    {
        this.discount = discount;
    }

    /**
     * Set the billing header title
     *
     */
    public void SetBillingHeaderTitle(String title)
    {
        this.billing_header_title = title;
    }

    /**
     * Set the file name of the logo that has to put into the billing header
     *
     */
    public void SetBillingLogoFilename(String logo_fname)
    {
        this.logo_fname = logo_fname;
    }

    /**
     * Set the resize method for the logo
     *
     */
    public void SetBillingLogoResizeMethod(LOGO_RESIZE_METHOD m)
    {
        this.logo_resize_method = m;
    }

    /**
     * Set the size of the logo
     *
     */
    public void SetBillingLogoAbsoluteSize(int width, int height)
    {
        this.logo_default_width = width;
        this.logo_default_height = height;
    }

    /**
     * Set the scaling percent of the logo
     *
     */
    public void SetBillingLogoScalingPercent(float p)
    {
        this.logo_scaling_percent_value = p;
    }


    private Paragraph createHeaderParagragh()
    {
        Paragraph header_paragraph = new Paragraph();
        PdfPTable header_table = new PdfPTable(1);

        //set the table width
        header_table.setWidthPercentage(100);

        //add the logo cell to the header table
        if (!logo_fname.isEmpty())
        {
            header_table.addCell(getBillingLogoCell()).setHorizontalAlignment(Element.ALIGN_CENTER);
        }

        //Set the header billing text
        PdfPCell text_cell = new PdfPCell(new Phrase(this.billing_header_title, PdfBillConfig.TITLE_FONT));
        text_cell.setBorder(0);
        text_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header_table.addCell(text_cell);

        //Add the table to the header paragraph
        header_paragraph.add(header_table);

        return header_paragraph;
    }

    /**
     * Create a table cell containing the logo of the billing
     */
    private PdfPCell getBillingLogoCell()
    {
        Image logo_image = null;

        try
        {
            logo_image = Image.getInstance(logo_fname);

            switch (this.logo_resize_method) {
                case Absolute -> logo_image.scaleToFit(logo_default_width, logo_default_height);
                case Percent -> logo_image.scalePercent(logo_scaling_percent_value);
            }
        } catch (BadElementException | IOException ex)
        {
            Logger.getLogger(BillGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Create the cell
        PdfPCell im_cell = new PdfPCell(logo_image);

        //Cell does not have border
        im_cell.setBorder(0);

        return im_cell;
    }


    private Paragraph createUserInformationParagraph() throws DocumentException
    {
        //create a new paragraph
        Paragraph user_information_paragraph = new Paragraph();

        //create the main paragraph table
        PdfPTable main_table = new PdfPTable(3);

        main_table.setWidths(new float[]
                {
                        100, 50, 200
                });

        //set the table expanded to the page width
        main_table.setWidthPercentage(100);

        //Create the user info table
        PdfPTable user_information_table = new PdfPTable(1);
        PdfPCell user_info_cell = new PdfPCell(getUserInformationPhrase());
        PdfPCell info_cell = new PdfPCell();

        user_information_table.setWidthPercentage(100);
        user_information_table.addCell(user_info_cell);
        info_cell.setBorderWidth(0);
        info_cell.addElement(user_information_table);

        //Create the  billing id table
        PdfPTable billing_id_table = new PdfPTable(1);
        PdfPCell billing_id_cell = new PdfPCell(getBillingIdentifierPhrase());
        PdfPCell billing_cell = new PdfPCell();

        billing_id_table.setWidthPercentage(100);
        billing_id_table.addCell(billing_id_cell);
        billing_cell.setBorderWidth(0);
        billing_cell.addElement(billing_id_table);
        PdfTableUtility.AddEmptyCellToTable(billing_id_table);
        PdfTableUtility.AddEmptyCellToTable(billing_id_table);

        //add the tables to the main table
        main_table.addCell(billing_cell);
        PdfTableUtility.AddEmptyCellToTable(main_table);
        main_table.addCell(info_cell);

        //add the main table to the paragraph
        user_information_paragraph.add(main_table);

        return user_information_paragraph;
    }


    private Phrase getBillingIdentifierPhrase()
    {
        Phrase billing_phrase = new Phrase();
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formatoCorto = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaCorto = fechaHoy.format(formatoCorto);

        Chunk billing = new Chunk("Billing:   ", PdfBillConfig.TABLE_CONTENT_FONT_1);
        Chunk id = new Chunk(this.billing_id, PdfBillConfig.TABLE_CONTENT_FONT_2);

        Chunk date = new Chunk("\nDate:   ", PdfBillConfig.TABLE_CONTENT_FONT_1);
        Chunk now = new Chunk(fechaCorto, PdfBillConfig.TABLE_CONTENT_FONT_2);

        billing_phrase.add(billing);
        billing_phrase.add(id);
        billing_phrase.add(date);
        billing_phrase.add(now);

        return billing_phrase;
    }


    private Phrase getUserInformationPhrase()
    {
        Phrase user_info = new Phrase();

        Chunk customer = new Chunk("Customer   ", PdfBillConfig.TABLE_CONTENT_FONT_1);
        Chunk customer_id_chunk = new Chunk(this.customer_name, PdfBillConfig.TABLE_CONTENT_FONT_2);

        Chunk email = new Chunk("E-mail   ", PdfBillConfig.TABLE_CONTENT_FONT_1);
        Chunk customer_email_chunk = new Chunk(this.customer_email, PdfBillConfig.TABLE_CONTENT_FONT_2);

        user_info.add(customer);
        user_info.add(customer_id_chunk);
        user_info.add("\n");
        user_info.add(email);
        user_info.add(customer_email_chunk);

        return user_info;
    }


    private Phrase getTotalBillingPricePhrase()
    {
        Phrase phrase = new Phrase();

        Chunk discount = new Chunk("Discount\n", PdfBillConfig.TABLE_CONTENT_FONT_1);
        Chunk total_discount = new Chunk(this.discount, PdfBillConfig.TABLE_CONTENT_FONT_2);

        Chunk init = new Chunk("\nTotal price\n", PdfBillConfig.TABLE_CONTENT_FONT_1);
        Chunk total_price = new Chunk(calculateTotalBillingPrice(), PdfBillConfig.TABLE_CONTENT_FONT_2);

        phrase.add(discount);
        phrase.add(total_discount);
        phrase.add(init);
        phrase.add(total_price);

        return phrase;
    }

    /**
     * Create the main billing paragraph that contains the table of products

     */
    private Paragraph createBillingBodyParagraph()
    {
        Paragraph main_paragraph = new Paragraph("");
        main_paragraph.setSpacingAfter(100);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSplitLate(false);
        table.setHeaderRows(2);
        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        //add the header row
        PdfTableUtility.AddRowToTable(table,
                new Phrase("Articuo", PdfBillConfig.TABLE_HEADER_FONT),
                new Phrase("Cantidad", PdfBillConfig.TABLE_HEADER_FONT),
                new Phrase("Precio Unitario", PdfBillConfig.TABLE_HEADER_FONT),
                new Phrase("Sub Total", PdfBillConfig.TABLE_HEADER_FONT));

        //add the footer row containing the total price information

        PdfTableUtility.AddEmptyCellToTable(table);
        PdfTableUtility.AddEmptyCellToTable(table);
        PdfTableUtility.AddEmptyCellToTable(table);
        PdfTableUtility.AddCellToTable(table, getTotalBillingPricePhrase());

        table.setFooterRows(1);

        //fill the billing tables with user data
        for (String[] data : billing_data)
        {
            PdfTableUtility.AddRowToTable(table, data[0], data[1], data[2],data[3]);
        }

        main_paragraph.add(Chunk.createWhitespace(""));
        main_paragraph.add(table);

        return main_paragraph;
    }

    /**
     * Calculate the sum of the prices of the articles in the billing.
     *
     * @return the string representing the total price of the articles
     */
    private String calculateTotalBillingPrice()
    {
        int total_price = 0;
        String total_price_str;

        for (String[] data : billing_data)
        {
            String priceStr = data[2];
            String quantity = data[1];
            if (priceStr.endsWith("$"))
            {
                priceStr = priceStr.substring(0, priceStr.length() - 1);
            }

            total_price += Float.parseFloat(priceStr) * Float.parseFloat(quantity);
        }
        total_price -= Float.parseFloat(this.discount);
        total_price_str = String.format(PdfBillConfig.CURRENCY_SYMBOL + "%d", total_price);

        return total_price_str;
    }



    //----------------------------------------------------------------------------------------------------------------


    public void GenerateBill(){
        Document document = new Document();
        try{
            String filePath = new File("").getAbsolutePath() + "/src/main/resources/Temp/" + "factura.pdf";
            // Crear un objeto PdfWriter para escribir el documento en un archivo PDF
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Abrir el documento
            document.open();

            // Agregar el encabezado
            Paragraph encabezado = new Paragraph("Factura");
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);

            // Agregar una imagen
            Image imagen = Image.getInstance("/Users/sebastiansulia/Documents/Tecnicatura_programacion/Semestre4/Proyecto - Buen sabor/BuenSaborV1/src/main/resources/Imges/f1f3e616-ba4b-42cd-83ed-996474862d7f-buensaborDiagram.png");
            imagen.setAlignment(Element.ALIGN_CENTER);
            document.add(imagen);

            // Agregar los detalles de la factura
            PdfPTable tabla = new PdfPTable(3); // 3 columnas
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            // Encabezados de las columnas
            PdfPCell celda1 = new PdfPCell(new Phrase("Producto"));
            tabla.addCell(celda1);

            PdfPCell celda2 = new PdfPCell(new Phrase("Cantidad"));
            tabla.addCell(celda2);

            PdfPCell celda3 = new PdfPCell(new Phrase("Precio"));
            tabla.addCell(celda3);

            // Detalles de la factura
            tabla.addCell("Producto 1");
            tabla.addCell("2");
            tabla.addCell("$10.00");

            tabla.addCell("Producto 2");
            tabla.addCell("1");
            tabla.addCell("$5.00");

            tabla.addCell("Producto 3");
            tabla.addCell("3");
            tabla.addCell("$8.00");

            document.add(tabla);

            // Cerrar el documento
            document.close();

            System.out.println("La factura se ha generado correctamente.");

        }catch (DocumentException | IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ByteArrayOutputStream GenerateDownloadableBill(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            Document document = new Document();

            // Crear un objeto PdfWriter para escribir el documento en un archivo PDF
            PdfWriter.getInstance(document, baos);

            // Abrir el documento
            document.open();

            // Agregar el encabezado
            Paragraph encabezado = new Paragraph("Factura");
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);

            // Agregar una imagen
            Image imagen = Image.getInstance("/Users/sebastiansulia/Documents/Tecnicatura_programacion/Semestre4/Proyecto - Buen sabor/BuenSaborV1/src/main/resources/Imges/f1f3e616-ba4b-42cd-83ed-996474862d7f-buensaborDiagram.png");
            imagen.setAlignment(Element.ALIGN_CENTER);
            document.add(imagen);

            // Agregar los detalles de la factura
            PdfPTable tabla = new PdfPTable(3); // 3 columnas
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            // Encabezados de las columnas
            PdfPCell celda1 = new PdfPCell(new Phrase("Producto"));
            tabla.addCell(celda1);

            PdfPCell celda2 = new PdfPCell(new Phrase("Cantidad"));
            tabla.addCell(celda2);

            PdfPCell celda3 = new PdfPCell(new Phrase("Precio"));
            tabla.addCell(celda3);

            // Detalles de la factura
            tabla.addCell("Producto 1");
            tabla.addCell("2");
            tabla.addCell("$10.00");

            tabla.addCell("Producto 2");
            tabla.addCell("1");
            tabla.addCell("$5.00");

            tabla.addCell("Producto 3");
            tabla.addCell("3");
            tabla.addCell("$8.00");

            document.add(tabla);

            // Cerrar el documento
            document.close();

            System.out.println("La factura se ha generado correctamente.");


        }catch (DocumentException | IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return baos;
    }



}
