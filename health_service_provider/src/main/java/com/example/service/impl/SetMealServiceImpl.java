package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.mapper.SetMealMapper;
import com.example.pojo.Setmeal;
import com.example.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealMapper.add(setmeal);
        Integer id = setmeal.getId();
        this.setSetMealAndCheckGroups(id,checkgroupIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Integer[] findCheckGroupIdsBySetMealId(Integer id) {
        return setMealMapper.selectCheckGroupIdsBySetMealId(id);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealMapper.update(setmeal);
        Integer id = setmeal.getId();
        setMealMapper.deleteCheckGroupIdsBySetMealId(id);
        this.setSetMealAndCheckGroups(id,checkgroupIds);
    }

    @Override
    public void delete(Integer id) {
        setMealMapper.deleteCheckGroupIdsBySetMealId(id);
        setMealMapper.deleteSetMealById(id);
    }

    public void setSetMealAndCheckGroups(Integer setMealId,Integer[] checkGroupIds){
        if (checkGroupIds != null && checkGroupIds.length > 0){
            for (Integer checkGroupId : checkGroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setMealId",setMealId);
                map.put("checkGroupId",checkGroupId);
                setMealMapper.setSetMealAndCheckGroups(map);
            }
        }
    }
}
