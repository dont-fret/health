package com.example.mapper;

import com.example.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {
    public List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    public Long findCountByOrderDate(String orderDate);

    public void editNumberByOrderDate(OrderSetting orderSetting);

    public void add(OrderSetting orderSetting);
}
