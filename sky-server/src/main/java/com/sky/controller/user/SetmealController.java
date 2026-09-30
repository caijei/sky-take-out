package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("UserSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags="C端-套餐浏览接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService  setmealService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result<List<Setmeal>> getSetmealByCategoryId(long categoryId){
        log.info("根据分类id查询套餐,{}",categoryId);
        Setmeal  setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> setmealList = setmealService.select(setmeal);
        return Result.success(setmealList);
    }
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> getDishBySetmealId(@PathVariable("id") long setmealId){
        log.info("根据套餐id查询包含的菜品,{}",setmealId);
        List<DishItemVO> dishItemVOList = setmealService.getDishBySetmealId(setmealId);
        return Result.success(dishItemVOList);
    }
}
