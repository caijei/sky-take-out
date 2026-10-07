package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    //查看购物车
    List<ShoppingCart> select(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart(name,image,user_id,dish_id,setmeal_id,dish_flavor,number," +
            "amount,create_time) values " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number}," +
            "#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    //@AutoFill(OperationType.UPDATE)
    void update(ShoppingCart shoppingCart);
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void delete(Long userId);
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);
}
