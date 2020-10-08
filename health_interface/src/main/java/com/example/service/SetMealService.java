package com.example.service;

import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.Setmeal;

public interface SetMealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public PageResult findPage(QueryPageBean queryPageBean);

    public Integer[] findCheckGroupIdsBySetMealId(Integer id);

    public void edit(Setmeal setmeal, Integer[] checkgroupIds);

    public void delete(Integer id);
}
