package com.briup.bean.vo;
import com.briup.bean.Category;
import lombok.Data;

import java.util.List;
@Data
public class CategoryVO {
    private Long id;
    private String name;
    private List<Category> categories;

    public CategoryVO(Category category, List<Category> categories) {
        // 一级类别
        this.id=category.getId();
        this.name=category.getName();
        // 该一级类别下的二级类别
        this.categories=categories;

    }
}
