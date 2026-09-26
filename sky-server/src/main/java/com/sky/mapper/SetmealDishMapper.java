package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealIdsByDishIds(@Param("dishIds") List<Long> dishIds);

    @Insert("insert into setmeal_dish(setmeal_id,dish_id,name,price,copies) values " +
            "(#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void save(SetmealDish setmealDish);
}
