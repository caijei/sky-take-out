package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    //新增地址
    void save(AddressBook addressBook);

    //查询用户的所有地址
    List<AddressBook> list(AddressBook addressBook);
    //设置默认地址
    void setDefaultAddress(AddressBook addressBook);
    //查询默认地址
    AddressBook queryDefaultAddress();
    //根据id获取地址
    AddressBook getById(Long id);
    //根据id删除地址
    void deleteById(Long id);
    //根据id修改地址
    void updateAddress(AddressBook addressBook);
}
