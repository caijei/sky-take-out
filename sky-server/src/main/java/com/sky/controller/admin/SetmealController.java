package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags="套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @PostMapping
    @ApiOperation(value = "添加套餐")
    public Result saveSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐，{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

}
