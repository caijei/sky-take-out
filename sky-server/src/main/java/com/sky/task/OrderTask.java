package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义定时任务，实现订单状态定时处理
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron="0 * * * * ? ")//每分钟处理
    //@Scheduled(cron="1/5 * * * * ? ")
    public void processTimeoutOrder(){
        log.info("处理超时订单，{}", new Date());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getBystatusAndOrdertime(Orders.PENDING_PAYMENT,time);
        if(ordersList != null && ordersList.size()>0){
            for(Orders orders : ordersList){
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("用户支付超时");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }
    //@Scheduled(cron="0/5 * * * * ? ")
    @Scheduled(cron="0 0 1 * * ? ") //每天凌晨1点处理
    public void processDeliveryOrder(){
        log.info("处理派送订单，{}", new Date());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getBystatusAndOrdertime(Orders.DELIVERY_IN_PROGRESS,time);
        if(ordersList != null && ordersList.size()>0){
            for(Orders orders : ordersList){
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }
}
