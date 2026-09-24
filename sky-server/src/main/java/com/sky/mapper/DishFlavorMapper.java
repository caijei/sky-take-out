package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//@Mapper 是 MyBatis 的注解，用来标识一个接口是数据库访问接口
public interface DishFlavorMapper {

    @Insert("insert into dish_flavor(dish_id,name,value) values " +
            "(#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);
}
