package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.Bill;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public interface BillService extends BaseService<Bill,Long> {

    ByteArrayOutputStream generateBillByOrderId(long id);
    List<Object> getBillingStatisticsCost(Date startDate, Date endDate);
    List<Object> getBillingStatisticsRevenue(Date startDate, Date endDate);
}
