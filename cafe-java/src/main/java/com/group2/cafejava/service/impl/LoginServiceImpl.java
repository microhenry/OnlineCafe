package com.group2.cafejava.service.impl;

import com.group2.cafejava.dto.LoginDTO;
import com.group2.cafejava.entity.Staff;
import com.group2.cafejava.entity.User;
import com.group2.cafejava.mapper.StaffMapper;
import com.group2.cafejava.mapper.UserMapper;
import com.group2.cafejava.result.Result;
import com.group2.cafejava.service.LoginService;
import com.group2.cafejava.vo.LoginStaffVO;
import com.group2.cafejava.vo.LoginVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result loginUser(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getLoginName())){
            System.out.println("Login name cannot be empty!");
            return new Result(400,"Login name cannot be empty!","");
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())){
            System.out.println("Password cannot be empty!");
            return new Result(400,"Password cannot be empty!","");
        }
        //通过登录名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name", loginDTO.getLoginName());
        User uer=userMapper.selectOne(wrapper);
        //比较密码
        System.out.println(uer == null);
        if (uer!=null&&uer.getPassword().equals(loginDTO.getPassword())){
            LoginVO loginVO=new LoginVO();
            loginVO.setId(uer.getId());
            //这里token直接用一个uuid
            //使用jwt的情况下，会生成一个jwt token,jwt token里会包含用户的信息
            loginVO.setToken(UUID.randomUUID().toString());
            loginVO.setUser(uer);
            return new Result(200,"",loginVO);
        }
        return new Result(401,"Login failed!","");
    }


    @Autowired
    private StaffMapper staffMapper;
    @Override
    public Result loginStaff(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getLoginName())){
            System.out.println("Login name cannot be empty!");
            return new Result(400,"Login name cannot be empty!","");
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())){
            System.out.println("Password cannot be empty!");
            return new Result(400,"Password cannot be empty!","");
        }
        //通过登录名查询用户
        QueryWrapper<Staff> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("login_name", loginDTO.getLoginName());
        Staff staff=staffMapper.selectOne(wrapper1);
        //比较密码
        System.out.println(staff == null);
        if (staff!=null&&staff.getPassword().equals(loginDTO.getPassword())){
            LoginStaffVO loginStaffVO=new LoginStaffVO();
            loginStaffVO.setId(staff.getId());
            //这里token直接用一个uuid
            //使用jwt的情况下，会生成一个jwt token,jwt token里会包含用户的信息
            loginStaffVO.setToken(UUID.randomUUID().toString());
            loginStaffVO.setStaff(staff);
            return new Result(200,"",loginStaffVO);
        }
        return new Result(401,"Login failed!","");
    }
}
