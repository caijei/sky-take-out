package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    //新增地址
    public void save(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }
    //查询用户的所有地址
    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        List<AddressBook> addressBookList  = addressBookMapper.query(addressBook);
        return addressBookList;
    }
    //设置默认地址
    @Override
    @Transactional
    public void setDefaultAddress(AddressBook addressBook) {
        //将当前用户的所有地址默认值都设置为 0,
        addressBookMapper.updateIsDefaultByUserId(
                AddressBook.
                builder().
                userId(BaseContext.getCurrentId()).
                isDefault(0).
                build());
        //根据地址id修改地址默认值
        addressBook.setIsDefault(1);
        System.out.println(addressBook);
        addressBookMapper.update(addressBook);
    }
    //查询默认地址
    @Override
    public AddressBook queryDefaultAddress() {
        //先获取用户id
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());

        addressBook.setIsDefault(1);
        List<AddressBook> addressBookList = addressBookMapper.query(addressBook);
        //存在默认地址
        if(addressBookList != null && addressBookList.size()>0){
            return addressBookList.get(0);
        }
        //不存在默认地址
        return null;
    }
    //根据id查询地址
    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        return addressBook;
    }
    //根据id删除地址
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }
    //根据id修改地址
    @Override
    public void updateAddress(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }
}
