package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    /*新增菜品和对应的口味*/
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //创建对象和拷贝数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //向表中插入一条数据,这个插入id会写
        dishMapper.insert(dish);
        //判断菜品口味是不是需要插入
        if(dishDTO.getFlavors() != null && dishDTO.getFlavors().size()>0){
            DishFlavor dishFlavor = new DishFlavor();
            for(DishFlavor temp:dishDTO.getFlavors()){
                BeanUtils.copyProperties(temp,dishFlavor);
                dishFlavor.setDishId(dish.getId());
                System.out.println(dishFlavor);
                dishFlavorMapper.insert(dishFlavor);
            }
        }
    }
}
