package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    //分页查询
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    //
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    //根据id查询套餐
    @Select("select * from setmeal where id = #{setmaelId}")
    Setmeal selectById(Long setmealId);
    //批量删除套餐
    void deleteSetmealIds(List<Long> setmealIds);

    List<Setmeal> select(Setmeal setmeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishBySetmealId(long setmealId);
}
