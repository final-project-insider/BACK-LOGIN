package com.insider.login.leave.controller;


import com.insider.login.leave.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(LeaveController.class)
@AutoConfigureMockMvc
public class LeaveControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaveService leaveService;

    public void testSelectLeaveSubmitListByMemberId() throws Exception {
        // given
        String requestBody = "{"
                + "\"memberId\": \"20240410-12312\","
                + "\"leaveSubStartDate\": \"2024-04-29\","
                + "\"leaveSubEndDate\": \"2024-04-30\","
                + "\"leaveSubApplyDate\": \"2024-04-21\","
                + "\"leaveSubType\": \"연차\""
                + "}";


        //when
        //then

    }
}
