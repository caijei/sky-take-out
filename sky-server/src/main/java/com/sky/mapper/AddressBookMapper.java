package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    @Insert("insert into address_book(user_id,consignee,sex,phone,province_code,province_name,city_code,city_name," +
            "district_code,district_name,detail,label,is_default) values " +
            "(#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName}," +
            "#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void insert(AddressBook addressBook);
    //
    List<AddressBook> query(AddressBook addressBook);

    void update(AddressBook addressBook);
    //根据id查询地址
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);
    //根据id删除地址
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
    //根据用户id修改地址的默认值
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);
}
