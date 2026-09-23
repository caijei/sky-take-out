package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //插入数据
    //在application.yml中已开启驼峰命名，故create_time和createTime可对应
    @Insert("insert into category(type, name, sort, status, create_time, update_time, " +
            "create_user,update_user)" +
            "values " +
            "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime}," +
            "#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    //根据id删除分类
    @Delete("delete from category where id = #{id}")
    void DeleteById(long id);
    /*修改分类*/
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    /*根据类型查询分类*/
    List<Category> list(Integer type);
}
