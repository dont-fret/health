package com.example.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.entity.Result;
import com.example.pojo.OrderSetting;
import com.example.service.OrderSettingService;
import com.example.untils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    //根据年月份查询预约设置信息
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/uploadNumberByOrderDate")
    public Result uploadNumberByOrderDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.uploadNumberByOrderDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try{
            List<String[]> excel = POIUtils.readExcel(excelFile);
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : excel) {
                String orderDate = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.valueOf(number));
                data.add(orderSetting);
            }
            orderSettingService.add(data);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

}
