package com.insider.login.approval.service;

import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.ApproverDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
//@AutoConfigureMockMvc
public class ApprovalServiceTest {

    @Autowired
    private ApprovalService approvalService;

//    @Autowired
//    private MockMvc mockMvc;

    private static Stream<Arguments> newForm(){
        return Stream.of(
                Arguments.of(
                        "con",
                        "<form><div></div></form>"
                )
        );
    }




/*
    @DisplayName("폼 양식 추가")
    @ParameterizedTest
    @MethodSource("newForm")
    public void testInsertForm(String form_no, String form_shape) {
        */
/*String requestBody = "{\"form_no\": \"con\", \"form_shape\": \"<form><div></div></form>\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/approval/")
                .contentType("application/x-www-form-urlencoded")
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        *//*


        Form newForm = new Form(form_no, form_name, form_shape);

        Assertions.assertDoesNotThrow(
                () -> approvalService.insertForm(newForm)
        );
    }
*/

    @Test
    void testInsertApproval(){
        //given
        ApprovalDTO approvalDTO = new ApprovalDTO();

        List<ApproverDTO> approverList  = new ArrayList<>();
        ApproverDTO approverDTO = new ApproverDTO("2024-con00001_apr001", "2024-con00001", 1, "처리 중", "2024-04-26 17:03:00", 2024001001);
        approverList.add(approverDTO);

        approvalDTO.setApprovalNo("2024-con00001");
        approvalDTO.setMemberId(2024001001);
        approvalDTO.setApprovalTitle("제목1");
        approvalDTO.setApprovalContent("내용1");
        approvalDTO.setApprovalDate("2024-04-26 17:00:00");
        approvalDTO.setApprovalStatus("처리 중");
        approvalDTO.setFormNo("con");

        approvalDTO.setApprover(approverList);

        //when
//        approvalService.insertApproval(approvalDTO);

        //then
        Assertions.assertDoesNotThrow(
                () -> approvalService.insertApproval(approvalDTO)
        );

    }


}
