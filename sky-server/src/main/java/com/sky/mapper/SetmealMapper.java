package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {
    /*
    根据分类id查询套餐的数量
    */
    /*因为 MyBatis 对“单个简单类型参数，且没有 @Param 注解”的情况
    有特殊处理：直接使用传入的参数值，不根据 #{...} 里的名字去查找对象属性。*/
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(long id);

    @Insert("insert into setmeal(category_id,name,price,status,description,image,create_time," +
            "update_time,create_user,update_user) values " +
            "(#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime}," +
            "#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")//表示id会会写到传入的参数属性中
    @AutoFill(OperationType.INSERT)
    void addSetmeal(Setmeal setmeal);
}
