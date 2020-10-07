package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.entity.CheckItemDeleteFailException;
import com.example.entity.PageResult;
import com.example.entity.QueryPageBean;
import com.example.mapper.CheckItemMapper;
import com.example.pojo.CheckItem;
import com.example.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;


    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.insert(checkItem);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.update(checkItem);
    }

    @Override
    public void delete(Integer id) throws CheckItemDeleteFailException {
        //判断检查项是否关联了检查组
        Long count = checkItemMapper.findCountByCheckItemId(id);
        if (count > 0){
            throw new CheckItemDeleteFailException();
        }
        checkItemMapper.deleteById(id);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用基于mybatis框架提供的pageHelper,完成分页查询
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemMapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemMapper.selectById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }
}
