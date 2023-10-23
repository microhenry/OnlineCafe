package com.group2.cafejava.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.User;
import com.group2.cafejava.result.Result;
import com.group2.cafejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    @PostMapping("/api/user/list")
    public Result userList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",userService.selectUserPage(queryDTO));
    }
    @PostMapping("/api/user/detail/{loginName}")
    public Result userDetail(@PathVariable String loginName){
        QueryDTO queryDTO=new QueryDTO();
        queryDTO.setPageNo(1);
        queryDTO.setPageSize(100);
        queryDTO.setKeyword(loginName);
        List<User> userSelect=userService.selectUser(queryDTO).getRecords();
        User select=new User();
        for (User i:userSelect){
            if (i.getLoginName().equals(loginName)){
                select=i;
                break;
            }
        }
        return new Result(200,"",select);
    }
    /**
     * 添加
     * @param user
     * @return
     */
    @PostMapping("/api/user/add")
    public Result addUser(@RequestBody User user){
        return new Result(200,"",userService.addUser(user));
    }

    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping("/api/user/update")
    public Result updateUser(@RequestBody User user){
        return new Result(200,"",userService.updateUser(user));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/api/user/delete")
    public Result deleteUser(Integer id){
        return new Result(200,"",userService.deleteUser(id));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/api/user/delete/batch")
    public Result batchDeleteUser(@RequestBody List<Integer> ids){
        userService.batchDelete(ids);
        return new Result(200,"","");
    }

}
