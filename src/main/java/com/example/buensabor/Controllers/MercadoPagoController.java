package com.example.buensabor.Controllers;


import com.example.buensabor.Models.MercadoPago.MpItem;
import com.example.buensabor.Services.Impl.OrderServiceImpl;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/mercadopago")
    public class MercadoPagoController {
        private OrderServiceImpl orderService;

    public MercadoPagoController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createAndRedirect")
        public ResponseEntity<?> createAndRedirect(@RequestBody MpItem mpItem) throws MPException, MPApiException, InterruptedException {
            PreferenceClient client = new PreferenceClient();

            List<PreferenceItemRequest> items = new ArrayList<>();

            PreferenceItemRequest item =
                    PreferenceItemRequest.builder()
                            .id(mpItem.getCode())
                            .title(mpItem.getTitle())
                            .description(mpItem.getDescription())
                            .quantity(1)
                            .currencyId("ARS")
                            .unitPrice(new BigDecimal(mpItem.getPrice()))
                            .build();
            items.add(item);


//            List<PreferenceTrackRequest> tracks = new ArrayList<>();
//            PreferenceTrackRequest googleTrack = PreferenceTrackRequest.builder().type("google_ad").build();
//
//            tracks.add(googleTrack);

            String urlSuccess = "http://localhost:8080/api/mercadopago/success";
            String urlFailure = "http://localhost:8080/api/mercadopago/failure";
            PreferenceBackUrlsRequest bu = PreferenceBackUrlsRequest.builder().success(urlSuccess).failure(urlFailure).pending(urlFailure).build();

            List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
            excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

            PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                    .excludedPaymentTypes(excludedPaymentTypes)
                    .installments(1)
                    .build();

            PreferenceRequest request = PreferenceRequest.builder()
                    .items(items)
                    .paymentMethods(paymentMethods)
                    .autoReturn("approved")
                    .externalReference(mpItem.getCode())
                    .backUrls(bu).build();

            //PreferenceRequest request = PreferenceRequest.builder().items(items).build();

            Preference p = client.create(request);

            String prefId = p.getId();

            return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\""+prefId+"\"}");
        }

        @GetMapping("/success")
        public RedirectView success(
                HttpServletRequest request,
                @RequestParam("collection_id") String collectionId,
                @RequestParam("collection_status") String collectionStatus,
                @RequestParam("external_reference") String externalReference,
                @RequestParam("payment_type") String paymentType,
                @RequestParam("merchant_order_id") String merchantOrderId,
                @RequestParam("preference_id") String preferenceId,
                @RequestParam("site_id") String siteId,
                @RequestParam("processing_mode") String processingMode,
                @RequestParam("merchant_account_id") String merchantAccountId,
                RedirectAttributes attributes)
                throws MPException {

            attributes.addFlashAttribute("genericResponse", true);
            attributes.addFlashAttribute("collection_id", collectionId);
            attributes.addFlashAttribute("collection_status", collectionStatus);
            attributes.addFlashAttribute("external_reference", externalReference);
            attributes.addFlashAttribute("payment_type", paymentType);
            attributes.addFlashAttribute("merchant_order_id", merchantOrderId);
            attributes.addFlashAttribute("preference_id",preferenceId);
            attributes.addFlashAttribute("site_id",siteId);
            attributes.addFlashAttribute("processing_mode",processingMode);
            attributes.addFlashAttribute("merchant_account_id",merchantAccountId);

            orderService.setOrderPaid(Long.valueOf(externalReference));
            return new RedirectView("http://127.0.0.1:5173/orderdetail/"+externalReference +"?success=true");
        }

    @GetMapping("/failure")
    public RedirectView failure(
            HttpServletRequest request,
            @RequestParam("collection_id") String collectionId,
            @RequestParam("collection_status") String collectionStatus,
            @RequestParam("external_reference") String externalReference,
            @RequestParam("payment_type") String paymentType,
            @RequestParam("merchant_order_id") String merchantOrderId,
            @RequestParam("preference_id") String preferenceId,
            @RequestParam("site_id") String siteId,
            @RequestParam("processing_mode") String processingMode,
            @RequestParam("merchant_account_id") String merchantAccountId,
            RedirectAttributes attributes)
            throws MPException {

        attributes.addFlashAttribute("genericResponse", true);
        attributes.addFlashAttribute("collection_id", collectionId);
        attributes.addFlashAttribute("collection_status", collectionStatus);
        attributes.addFlashAttribute("external_reference", externalReference);
        attributes.addFlashAttribute("payment_type", paymentType);
        attributes.addFlashAttribute("merchant_order_id", merchantOrderId);
        attributes.addFlashAttribute("preference_id",preferenceId);
        attributes.addFlashAttribute("site_id",siteId);
        attributes.addFlashAttribute("processing_mode",processingMode);
        attributes.addFlashAttribute("merchant_account_id",merchantAccountId);

        try {
            orderService.delete(Long.valueOf(externalReference));
        }catch (Exception e){
            System.out.println("No se pudo eliminar la orden");
        }

        return new RedirectView("http://127.0.0.1:5173/cart?failure=true");
    }



}
