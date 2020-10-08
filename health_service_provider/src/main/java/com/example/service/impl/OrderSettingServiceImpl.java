package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.mapper.OrderSettingMapper;
import com.example.pojo.OrderSetting;
import com.example.service.OrderSettingService;
import com.example.untils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;


    @Override
    public List<Map> getOrderSettingByMonth(String date) throws Exception {
        String begin = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth(date,"yyyy-MM"));
        String end = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth(date,"yyyy-MM"));
        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                Map<String,Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public void uploadNumberByOrderDate(OrderSetting orderSetting) throws Exception {
        Long count = orderSettingMapper.findCountByOrderDate(DateUtils.parseDate2String(orderSetting.getOrderDate()));
        if (count > 0){
            //已经被预约设置过,更新预约设置
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        }else {
            //还没有进行预约设置,执行添加预约设置操作
            orderSettingMapper.add(orderSetting);
        }
    }


    @Override
    public void add(List<OrderSetting> data) throws Exception {
        if (data != null && data.size() > 0){
            for (OrderSetting orderSetting : data) {
                Long count = orderSettingMapper.findCountByOrderDate(DateUtils.parseDate2String(orderSetting.getOrderDate()));
                if (count > 0){
                    //已经被预约设置过,更新预约设置
                    orderSettingMapper.editNumberByOrderDate(orderSetting);
                }else {
                    //还没有进行预约设置,执行添加预约设置操作
                    orderSettingMapper.add(orderSetting);
                }
            }
        }
    }
}
