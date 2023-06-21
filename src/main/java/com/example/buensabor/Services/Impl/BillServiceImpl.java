package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.Bill;
import com.example.buensabor.Models.Entity.Order;
import com.example.buensabor.Repositories.BillRepository;
import com.example.buensabor.Services.BillService;
import com.example.buensabor.Services.PdfService.BillGenerator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl extends BaseServiceImpl<Bill,Long> implements BillService {

    private BillRepository billRepository;

    public BillServiceImpl(BillRepository billRepository) {
        super(billRepository);
        this.billRepository = billRepository;
    }

    @Override
    public ByteArrayOutputStream generateBillByOrderId(long id) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Bill bill = billRepository.findByOrderId(id);
            Order order = bill.getOrder();

            bill=null;
            //create the billing
            BillGenerator b = new BillGenerator();

            //Set the billing title
            b.SetBillingHeaderTitle("Factura Buen Sabor");

    //      Set the billing logo
            String logoPath = new File("").getAbsolutePath() + "/src/main/resources/Imges/logo/BuenSaborLogo.jpeg";
            b.SetBillingLogoFilename(logoPath);
            b.SetBillingLogoResizeMethod(BillGenerator.LOGO_RESIZE_METHOD.Percent);
            b.SetBillingLogoScalingPercent(50);

            //Add entries to billing
            b.SetDiscount(String.valueOf(order.getDiscount()));
            order.getOrderDetails().forEach(orderDetail ->
                    b.AddBillingEntry(orderDetail.getProduct().getName(),
                            String.valueOf(orderDetail.getQuantity()),
                            String.valueOf(orderDetail.getProduct().getSellPrice())));

            //Set the customer data

            b.SetCustomerEmail(order.getUser().getUserEmail());
            b.SetCustomerName(order.getUser().getName() + " " +order.getUser().getLastName());
            b.SetBillingIdentifier("Order_" + order.getId());

            //Generate the billing
            baos = b.GenerateDocument();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return baos;
    }

    @Override
    public List<Object> getBillingStatisticsCost(Date startDate, Date endDate) {
        return billRepository.getBillingStatisticsCosts(startDate,endDate);
    }
    @Override
    public List<Object> getBillingStatisticsRevenue(Date startDate, Date endDate) {
        return billRepository.getBillingStatistics(startDate,endDate);
    }

    public boolean validateNonDuplicateBill(long orderId){
        Bill bill = billRepository.findByOrderId(orderId);

        if (bill == null){
            return true;
        }else
            return false;
    }

}
