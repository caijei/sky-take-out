package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /*根据分类id查询菜品数量*/
    /*因为 MyBatis 对“单个简单类型参数，且没有 @Param 注解”的情况
    有特殊处理：直接使用传入的参数值，不根据 #{...} 里的名字去查找对象属性。*/
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(long id);

    @Insert("insert into dish(name,category_id,price,image,description,status,create_time," +
            "update_time,create_user,update_user) values " +
            "(#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime}," +
            "#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")//表示id会会写到传入的参数属性中
    void insert(Dish dish);

    /*菜品分页查询*/
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
