package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealIdsByDishIds(@Param("dishIds") List<Long> dishIds);

    //添加套餐和菜品的关系
    @Insert("insert into setmeal_dish(setmeal_id,dish_id,name,price,copies) values " +
            "(#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void save(SetmealDish setmealDish);
    //根据id查找
    @Select("select * from setmeal_dish where setmeal_id = #{setmeal_Id}")
    List<SetmealDish> selectBySetmealId(long setmealId);

    @Delete("delete from setmeal_dish where setmeal_id = #{setmealID}")
    void deleteByDishId(Long setmealId);

    void deleteByDishIds(List<Long> dishIds);
}
