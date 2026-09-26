package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /*菜品分页查询*/
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /*新增菜品和对应的口味*/
    void saveWithFlavor(DishDTO dishDTO);
    /*根据id集合批量删除菜品*/
    void deleteBatch(List<Long> ids);
    //启用或禁用菜品
    void startOrStop(long id, Integer status);
    //根据id获取菜品
    DishVO getDishById(Long id);
    //修改菜品
    void updateDish(DishDTO dishDTO);
    //根据分类id查询菜品
    List<Dish> getDishByCategoryId(Long categoryId);
}
