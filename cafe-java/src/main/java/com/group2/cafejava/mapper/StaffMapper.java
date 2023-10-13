package com.group2.cafejava.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cafejava.entity.Staff;



public interface StaffMapper extends BaseMapper<Staff> {
    IPage<Staff> selectUserPage(Page<Staff> page, String keyword);
}
