package com.insider.login.leave.controller;

import com.insider.login.leave.service.LeaveService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }


}
