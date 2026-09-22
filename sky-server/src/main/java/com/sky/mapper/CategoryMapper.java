package com.sky.mapper;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    //在application.yml中已开启驼峰命名，故create_time和createTime可对应
    @Insert("insert into category(type, name, sort, status, create_time, update_time, " +
            "create_user,update_user)" +
            "values " +
            "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime}," +
            "#{createUser},#{updateUser})")
    void insert(Category category);
}
