package com.group2.cafejava.service;

import com.group2.cafejava.dto.LoginDTO;
import com.group2.cafejava.result.Result;

public interface LoginService {
    public Result login(LoginDTO loginDTO);
}
