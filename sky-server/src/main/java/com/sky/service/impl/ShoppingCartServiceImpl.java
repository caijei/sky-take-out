package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.DishService;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    //查看购物车
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {


        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
// 查询购物车是否存在这个商品
        //获取当前的用户id
        long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //查询购物车是否存在改菜品
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.select(shoppingCart);

        // 如果存在商品,则数量增加1
        if(shoppingCartList != null && shoppingCartList.size() == 1){
            shoppingCart = shoppingCartList.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.update(shoppingCart);
        }else{
            //不存在向购物车添加商品
            Long dishId = shoppingCart.getDishId();
            //如果添加的是菜品
            if(dishId != null){
                Dish  dish = dishMapper.selectById(dishId);
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setName(dish.getName());
            }else{
                //添加的是套餐
                Setmeal setmeal = setmealMapper.selectById(shoppingCart.getSetmealId());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setName(setmeal.getName());
            }
            //初始数量设置为1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            System.out.println(shoppingCart);
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    // 查看购物车
    @Override
    public List<ShoppingCart> getShoppingCart() {
        long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.select(shoppingCart);
        return shoppingCartList;
    }

    //清空购物车
    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.delete(BaseContext.getCurrentId());
    }
    //删除购物车中一个商品
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //只能删除自己的购物车物品
        ShoppingCart  shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        // 先查询购物车中这个菜品的数量
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.select(shoppingCart);

        //购物车存在这个菜品
        if(shoppingCartList != null && shoppingCartList.size() == 1){
            shoppingCart =  shoppingCartList.get(0);
            //如果菜品数量大于1，菜品数量减1
            if(shoppingCart.getNumber() > 1){
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.update(shoppingCart);
            }else{
                //如果菜品数量等于1，直接删除菜品
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }
        }
    }
}
