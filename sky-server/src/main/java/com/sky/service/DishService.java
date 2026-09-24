package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    /*菜品分页查询*/
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /*新增菜品和对应的口味*/
    void saveWithFlavor(DishDTO dishDTO);
}
