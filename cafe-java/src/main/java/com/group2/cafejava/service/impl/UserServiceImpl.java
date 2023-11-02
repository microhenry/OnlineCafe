package com.group2.cafejava.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.User;
import com.group2.cafejava.mapper.UserMapper;
import com.group2.cafejava.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> selectUserPage(QueryDTO queryDTO) {
        Page<User> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return userMapper.selectUserPage(page,queryDTO.getKeyword());
    }

    @Override
    public Integer addUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name", user.getLoginName());
        User uer=userMapper.selectOne(wrapper);
        if (uer==null){
            user.setMoney(200);
            return userMapper.insert(user);}
        else{
            return -1;
        }
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<User> selectUser(QueryDTO queryDTO) {
        Page<User> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return userMapper.selectUser(page,queryDTO.getKeyword());
    }

}
