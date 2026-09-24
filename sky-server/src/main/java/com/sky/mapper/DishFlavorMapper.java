package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
//@Mapper 是 MyBatis 的注解，用来标识一个接口是数据库访问接口
public interface DishFlavorMapper {

    @Insert("insert into dish_flavor(dish_id,name,value) values " +
            "(#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);
    //根据id删除菜品口味
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    //根据id集合批量删除菜品口味
    void deleteByDishIds(List<Long> dishIds);

    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> selectByDishId(Long dishId);
}
