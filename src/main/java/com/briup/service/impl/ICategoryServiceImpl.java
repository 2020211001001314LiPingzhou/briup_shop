package com.briup.service.impl;

import com.briup.bean.Category;
import com.briup.bean.vo.CategoryVO;
import com.briup.dao.ICategoryDao;
import com.briup.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ICategoryServiceImpl implements ICategoryService {
    @Resource
    private ICategoryDao categoryDao;

    @Override
    public List<CategoryVO> findAllCategory() {
        // 查询所有的一级类别
        List<Category> Categories = categoryDao.findByParentIdIsNull();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        // 遍历查询一级类别下的二级类别
        Categories.forEach(category -> {
            List<Category> byParentId = categoryDao.findByParentId(category.getId());
            CategoryVO categoryVO = new CategoryVO(category, byParentId);
            categoryVOList.add(categoryVO);
        });

        return categoryVOList;
    }
}
