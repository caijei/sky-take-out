package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 插入数据
     * @param orderDetail
     */
    @Insert("insert into order_detail(name,image,order_id,dish_id,setmeal_id,dish_flavor,number,amount)" +
            "values (#{name},#{image},#{orderId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount})")
    void insert(OrderDetail orderDetail);

    /**
     * 根据订单id查询订单的详情
     * @param orderId
     * @return
     */
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
