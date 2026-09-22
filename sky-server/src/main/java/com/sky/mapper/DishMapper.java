package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /*根据分类id查询菜品数量*/
    /*因为 MyBatis 对“单个简单类型参数，且没有 @Param 注解”的情况
    有特殊处理：直接使用传入的参数值，不根据 #{...} 里的名字去查找对象属性。*/
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(long id);
}
