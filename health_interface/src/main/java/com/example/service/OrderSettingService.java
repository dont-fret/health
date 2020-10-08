package com.example.service;

import com.example.pojo.OrderSetting;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    public List<Map> getOrderSettingByMonth(String date) throws Exception;

    public void uploadNumberByOrderDate(OrderSetting orderSetting) throws Exception;

    public void add(List<OrderSetting> data) throws Exception;
}
