package com.example.mapper;

import com.example.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface CheckItemMapper {
    public CheckItem selectById(Integer id);

    public void update(CheckItem checkItem);

    public void insert(CheckItem checkItem);

    public void deleteById(Integer id);

    public Long findCountByCheckItemId(Integer id);

    public Page<CheckItem> selectByCondition(String queryString);

    public List<CheckItem> findAll();
}
