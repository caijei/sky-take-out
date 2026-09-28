package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags="套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation(value = "添加套餐")
    //添加套餐
    public Result saveSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐，{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    //套餐分页查询
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    //套餐的启用或禁用
    @PostMapping("status/{status}")
    @ApiOperation("套餐启用或禁用")
    public Result startOrStopSetmeal(@PathVariable("status")Integer status,long id ) {
        log.info("套餐启用或禁用，{}，{}", status, id);
        setmealService.startOrStop(status,id);
        return Result.success();
    }
    @DeleteMapping()
    @ApiOperation("批量删除套餐")
    //批量删除套餐
    public Result deleteSetmeals(@RequestParam("ids") List<Long> setmealIds) {
        log.info("批量删除套餐,{}", setmealIds);
        setmealService.deleteSetmeals(setmealIds);
        return Result.success();
    }
    //根据id查询套餐
    @GetMapping("/{id}")
    public Result<SetmealVO> selectBySetmealId(@PathVariable long id) {
        log.info("根据id查询套餐，{}",id);
        SetmealVO setmealVO = setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation(value = "修改套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐, {}", setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
}
