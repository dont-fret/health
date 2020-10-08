package com.example.service;

import com.example.entity.CheckItemDeleteFailException;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);

    public void edit(CheckItem checkItem);

    public void delete(Integer id) throws CheckItemDeleteFailException;

    public PageResult findPage(QueryPageBean queryPageBean);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
}
