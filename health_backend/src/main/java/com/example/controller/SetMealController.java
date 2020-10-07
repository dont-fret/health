package com.example.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.constant.RedisConstant;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.entity.Result;
import com.example.pojo.Setmeal;
import com.example.service.SetMealService;
import com.example.untils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        try {
            String originalFilename = imgFile.getOriginalFilename();//原始文件名
            int index = originalFilename.lastIndexOf(".");
            String extention = originalFilename.substring(index - 1);//后缀
            String fileName = UUID.randomUUID().toString()+extention;//生成图片名
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        }catch (Exception e){
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }



    //添加套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealService.add(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return setMealService.findPage(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //查询套餐关联的检查组信息
    @RequestMapping("/findCheckGroupIdsBySetMealId")
    public Result findCheckGroupIdsBySetMealId(Integer id){
        try {
            Integer[] checkGroupIds = setMealService.findCheckGroupIdsBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //编辑套餐
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealService.edit(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    //删除套餐
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setMealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }


}
