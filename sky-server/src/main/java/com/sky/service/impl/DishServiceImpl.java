package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    /*菜品分页查询*/
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        long total = page.getTotal();
        List<DishVO> records = page.getResult();
        return new PageResult(total,records);
    }

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
    /*根据id集合批量删除菜品*/
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //起售的菜品不能删除
        for(Long id:ids){
            Dish dish = dishMapper.selectById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                //起售中的菜品不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //判断当前菜品是否能够删除---是否被套餐关联了？？
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            //当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        /*//循环单词,容易引发性能问题
        for(Long id:ids) {
            //先删除口味，再删除菜品
            dishFlavorMapper.deleteByDishId(id);
            dishMapper.deleteById(id);
        }*/
        dishFlavorMapper.deleteByDishIds(ids);
        dishMapper.deleteByIds(ids);
    }

    @Override
    public void startOrStop(long id, Integer status) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }
}
