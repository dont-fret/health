package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.mapper.CheckGroupMapper;
import com.example.pojo.CheckGroup;
import com.example.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupMapper.add(checkGroup);
        Integer id = checkGroup.getId();
        this.setCheckGroupAndCheckItems(id,checkItemIds);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupMapper.update(checkGroup);
        Integer id = checkGroup.getId();

        checkGroupMapper.deleteCheckItemIdsByCheckGroupId(id);
        this.setCheckGroupAndCheckItems(id,checkItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupMapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.selectById(id);
    }

    @Override
    public Integer[] findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.selectCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void delete(Integer id) {
        checkGroupMapper.deleteCheckItemIdsByCheckGroupId(id);
        checkGroupMapper.deleteCheckGroupById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.selectAll();
    }

    public void setCheckGroupAndCheckItems(Integer checkGroupId,Integer[] checkItemIds){
        if (checkItemIds != null && checkItemIds.length > 0 ){
            for (Integer checkItemId : checkItemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkItemId",checkItemId);
                checkGroupMapper.setCheckGroupAndCheckItem(map);
            }

        }
    }
}
