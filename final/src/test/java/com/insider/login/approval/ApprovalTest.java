//package com.insider.login.approval;
//
//import com.insider.login.approval.entity.Form;
//import com.insider.login.approval.service.ApprovalService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.stream.Stream;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ApprovalTest {
//
//    @Autowired
//    private ApprovalService approvalService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static Stream<Arguments> newForm(){
//        return Stream.of(
//                Arguments.of(
//                        "con",
//                        "<form><div></div></form>"
//                )
//        );
//    }
//
//
//    @DisplayName("폼 양식 추가")
//    @ParameterizedTest
//    @MethodSource("newForm")
//    public void testInsertForm(String form_no, String form_shape) {
//        /*String requestBody = "{\"form_no\": \"con\", \"form_shape\": \"<form><div></div></form>\"}";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/approval/")
//                .contentType("application/x-www-form-urlencoded")
//                .content(requestBody))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        */
//
//        Form newForm = new Form(form_no, form_shape);
//
//        Assertions.assertDoesNotThrow(
//                () -> approvalService.insertForm(newForm)
//        );
//    }
//
//
//
//
//}
