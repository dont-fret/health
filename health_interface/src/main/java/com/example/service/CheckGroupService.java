package com.example.service;

import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkItemIds);

    public void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    public PageResult findPage(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public Integer[] findCheckItemIdsByCheckGroupId(Integer id);

    public void delete(Integer id);

    public List<CheckGroup> findAll();

}
