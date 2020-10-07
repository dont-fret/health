package com.example.mapper;

import com.example.pojo.Setmeal;
import com.github.pagehelper.Page;
import java.util.Map;

public interface SetMealMapper {
    public void add(Setmeal checkGroup);

    public void setSetMealAndCheckGroups(Map<String, Integer> map);

    public void update(Setmeal checkGroup);

    public Page<Setmeal> selectByCondition(String queryString);

    public Integer[] selectCheckGroupIdsBySetMealId(Integer id);

    public void deleteCheckGroupIdsBySetMealId(Integer id);

    public void deleteSetMealById(Integer id);

}
