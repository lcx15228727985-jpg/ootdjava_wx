package com.fitting.app.controller;

import com.fitting.app.common.Result;
import com.fitting.app.entity.User;
import com.fitting.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String code = params.get("code");

        // 查询用户是否存在
        User existUser = userService.lambdaQuery()
                .eq(User::getOpenid, code)
                .one();

        if (existUser == null) {
            // 不存在则自动注册
            User newUser = new User();
            newUser.setOpenid(code);
            newUser.setNickname("用户" + code.substring(0, Math.min(6, code.length())));
            newUser.setCreateTime(LocalDateTime.now());
            userService.save(newUser);
            existUser = newUser;
        }

        // 返回用户信息
        Map<String, Object> data = new HashMap<>();
        data.put("id", existUser.getId());
        data.put("openid", existUser.getOpenid());
        data.put("nickname", existUser.getNickname());
        data.put("createTime", DateUtil.format(existUser.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

        return Result.success(data);
    }
}