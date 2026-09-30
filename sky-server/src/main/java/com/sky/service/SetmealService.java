package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    //添加套餐
    void addSetmeal(SetmealDTO setmealDTO);

    //分页查询
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    //套餐的启用或禁用
    void startOrStop(Integer status, long id);
    //批量删除套餐
    void deleteSetmeals(List<Long> setmealIds);
    //根据id查询类型
    SetmealVO selectById(long id);

    void updateSetmeal(SetmealDTO setmealDTO);

    List<Setmeal> select(Setmeal setmeal);

    List<DishItemVO> getDishBySetmealId(long setmealId);
}
