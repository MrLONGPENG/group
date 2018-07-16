package com.mujugroup.lock.controller;

import com.mujugroup.lock.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/putToken")
    public String putToken(){
        return authService.putToken();
    }


    @RequestMapping(value = "/getToken")
    public String getToken(){
        return authService.getToken();
    }
}
