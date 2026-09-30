package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags= "C端-菜品浏览接口")
@Slf4j
public class DishController {

    @Autowired
    private  DishService dishService;

    @Autowired
    private RedisTemplate  redisTemplate;
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> getDishByCategoryId(long categoryId){
        log.info("分类id，{}",categoryId);

        //构造redis中的key，规则：dish_分类id
        String key = "dish_" + categoryId;
        //根据key先查询redis中是否存在数据
        List<DishVO> dishVOS = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishVOS != null && dishVOS.size()>0){
            //如果存在，直接返回，无须查询数据库
            return Result.success(dishVOS);
        }
        //如果不存在，查询数据库，将查询到的数据放入redis中
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        dishVOS = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key,dishVOS);

        return Result.success(dishVOS);
    }
}
