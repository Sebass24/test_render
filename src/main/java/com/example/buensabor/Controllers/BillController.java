package com.example.buensabor.Controllers;

import java.io.ByteArrayOutputStream;
import com.example.buensabor.Models.Entity.Bill;
import com.example.buensabor.Services.Email.MailService;
import com.example.buensabor.Services.Impl.BillServiceImpl;
import com.example.buensabor.Services.PdfService.BillGenerator;
import com.example.buensabor.Util.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.buensabor.Services.PdfService.BillGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/bill")
public class BillController extends BaseControllerImpl<Bill, BillServiceImpl>{

    private MailService mailService;
    public BillController(BillServiceImpl service, MailService mailService) {
        super(service);
        this.mailService = mailService;
    }

    @GetMapping("testemail")
    public ResponseEntity<?> sendTestEmail(){
        try {
            mailService.testEmail();
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\":\"Email sent\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("test")
    public ResponseEntity<?> test(){
        try {
            service.generateBillByOrderId(34);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\":\"Email sent\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("/download-bill/{id}")
    public ResponseEntity<byte[]> downloadBillByOrderId(HttpServletResponse response, @PathVariable Long id) throws IOException {

        ByteArrayOutputStream baos = service.generateBillByOrderId(id);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            // Configurar encabezados de respuesta
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");
            response.setContentLength(baos.size());

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        finally {
            Util.deleteTemp();
        }
        return ResponseEntity.ok().body(baos.toByteArray());
    }

    @GetMapping("GetBillingStatisticsCosts")
    public ResponseEntity<?> GetBillingStatistics(@RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getBillingStatisticsCost(startDate,endDate));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("GetBillingStatisticsRevenue")
    public ResponseEntity<?> GetBillingStatisticsRevenue(@RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getBillingStatisticsRevenue(startDate,endDate));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
//    @GetMapping("testbill")
//    public ResponseEntity<?> createBill(){
//        try {
//            billGeneratorService.GenerateBill();
//            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\":\"imagen generada\"}");
//        }
//        catch (Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
//        }
//    }
//
//    @GetMapping("/download-bill")
//    public ResponseEntity<byte[]> descargarPDF(HttpServletResponse response) throws IOException {
//        ByteArrayOutputStream baos = billGeneratorService.GenerateDownloadableBill();
//
//        // Configurar encabezados de respuesta
//        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//        response.setHeader("Content-Disposition", "attachment; filename=archivo.pdf");
//        response.setContentLength(baos.size());
//
//        return ResponseEntity.ok().body(baos.toByteArray());
//    }
}
