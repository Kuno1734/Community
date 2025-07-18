package com.icom.community.domain.user.controller;


import com.icom.community.common.response.ApiResponse;
import com.icom.community.domain.user.dto.UserCreateRequest;
import com.icom.community.domain.user.dto.UserResponse;
import com.icom.community.domain.user.dto.UserUpdateRequest;
import com.icom.community.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ApiResponse<List<UserResponse>> index(){
       return userService.findAllUser();
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> registUser(@RequestBody UserCreateRequest request){
        return userService.createUser(request);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> indUserInfo(@PathVariable long userId){
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public ApiResponse<UserResponse> updateUserInfo(@PathVariable long userId , @RequestBody UserUpdateRequest request){
        return userService.updateUserInfo(userId, request);
    }


}
