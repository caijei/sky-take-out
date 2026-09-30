package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper) {
        this.setmealMapper = setmealMapper;
    }

    @Override
    //添加套餐
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        //先添加套餐数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);//初始套餐要设置为禁售
        setmealMapper.addSetmeal(setmeal);//添加套餐返回套餐id
        //再添加套餐和菜品的数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes!=null && setmealDishes.size()>0){
            for(SetmealDish setmealDish:setmealDishes){
                setmealDish.setSetmealId(setmeal.getId());
                setmealDishMapper.save(setmealDish);
            }

        }
    }

    //套餐分页查询
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        long total = page.getTotal();
        List<SetmealVO> setmealVOs = page.getResult();
        return new PageResult(total,setmealVOs);
    }

    //套餐启用或禁用
    @Override
    public void startOrStop(Integer status, long id) {
        //创建套餐类,匹配动态修改
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);
        //根据套餐id在表setmeal_dish中获得关联的菜品
        List<SetmealDish>  setmealDishes = setmealDishMapper.selectBySetmealId(id);
        //如果存在关联的菜品
        if(setmealDishes!=null && setmealDishes.size()>0){
            for (SetmealDish setmealDish : setmealDishes) {
                //根据菜品id获取菜品
                    Dish dish = dishMapper.selectById(setmealDish.getDishId());
                    //如果菜品处于禁售状态,不能启用套餐
                    if(dish.getStatus() == StatusConstant.DISABLE){
                        //套餐内包含未启售菜品，无法启售
                        throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }
        setmealMapper.update(setmeal);
    }

    @Override
    @Transactional
    public void deleteSetmeals(List<Long> setmealIds) {
        for(Long setmealId:setmealIds){
            //查找获取套餐对象
            Setmeal setmeal = setmealMapper.selectById(setmealId);
            Integer status = setmeal.getStatus();
            //判断套餐是否停售或起售
            if(status==StatusConstant.ENABLE){
                ///起售的商品不能删除
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        setmealMapper.deleteSetmealIds(setmealIds);
        setmealDishMapper.deleteByDishIds(setmealIds);
    }

    @Override
    //根据id查询类型
    public SetmealVO selectById(long id) {
        //先返回套餐
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setmealMapper.selectById(id);
        BeanUtils.copyProperties(setmeal,setmealVO);
        //再返回和套餐关联的菜品
        List<SetmealDish> setmealDishList = setmealDishMapper.selectBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishList);

        return setmealVO;
    }

    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);

        setmealDishMapper.deleteByDishId(setmealDTO.getId());

        List<SetmealDish> setmealDishs = setmealDTO.getSetmealDishes();

        if(setmealDishs!=null && setmealDishs.size()>0){
            for(SetmealDish setmealDish:setmealDishs){
                setmealDish.setSetmealId(setmealDTO.getId());
                //System.out.println(setmealDish.getSetmealId());
                setmealDishMapper.save(setmealDish);
            }
        }
    }

    @Override
    public List<Setmeal> select(Setmeal setmeal) {
        List<Setmeal>  setmealList = setmealMapper.select(setmeal);
        return setmealList;
    }

    @Override
    public List<DishItemVO> getDishBySetmealId(long setmealId) {
        List<DishItemVO> dishItemVOS = setmealMapper.getDishBySetmealId(setmealId);
        return dishItemVOS;
    }
}
