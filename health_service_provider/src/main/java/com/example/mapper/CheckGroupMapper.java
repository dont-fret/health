package com.example.mapper;

import com.example.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {
    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(Map<String, Integer> map);

    public void update(CheckGroup checkGroup);

    public Page<CheckGroup> selectByCondition(String queryString);

    public CheckGroup selectById(Integer id);

    public Integer[] selectCheckItemIdsByCheckGroupId(Integer id);

    public void deleteCheckItemIdsByCheckGroupId(Integer id);

    public void deleteCheckGroupById(Integer id);

    public List<CheckGroup> selectAll();
}
