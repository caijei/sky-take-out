package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /*
     * 分类分页查询
     * */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /*
     * 根据id删除分类*/
    void deleteById(long id);

    /*修改分类*/
    void update(CategoryDTO categoryDTO);

    /*启用或禁用分类*/
    void startOrStop(Integer status, Long id);

    /*根据类型查询分类*/
    List<Category> list(Integer type);
}