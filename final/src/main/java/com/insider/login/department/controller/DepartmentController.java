package com.insider.login.department.controller;

import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.dto.DepartmentDetailsDTO;
import com.insider.login.department.service.DepartmentService;
import com.insider.login.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;
    private final MemberService memberService;

    public DepartmentController (DepartmentService departmentService, MemberService memberService) {
        this.departmentService = departmentService;
        this.memberService = memberService;
    }


    /** 부서 등록 */
    @PostMapping("/departments")
    public String insertDepartment(@RequestBody DepartmentDTO departmentDTO) {

        departmentService.insertDepartment(departmentDTO);
        return "저장 완료";
    }

    /** 부서 조회 */
    @GetMapping("/departments")
    public List<DepartmentDetailsDTO> allDepartments() {
        List<DepartmentDTO> departmentDTOs = departmentService.searchAllDepartment();
        List<DepartmentDetailsDTO> departmentDetailsDTOList = new ArrayList<>();

        for (DepartmentDTO departmentDTO : departmentDTOs) {
            int noOfMembersInDepartment = membersInDepartments(departmentDTO.getDepartNo());
            DepartmentDetailsDTO departmentDetailsDTO = new DepartmentDetailsDTO();

            departmentDetailsDTO.setDepartName(departmentDTO.getDepartName());
            departmentDetailsDTO.setDepartNo(departmentDTO.getDepartNo());
            departmentDetailsDTO.setNoOfPeople(noOfMembersInDepartment);

            departmentDetailsDTOList.add(departmentDetailsDTO);
        }
        return departmentDetailsDTOList;
    }

    /** 부서 수정 */
    @PutMapping("/departments/{departNo}")
    public String updateDepartName(@PathVariable("departNo") int departNo, @RequestBody DepartmentDTO departmentDTO) {
        departmentDTO.setDepartNo(departNo);
        String existingDepartName = departmentService.findDepartName(departNo);

        /* 부서명이 이미 존재한다면 */
        if (existingDepartName.equals(departmentDTO.getDepartName())) {
            return "Department Name already exists";
        } else {
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO);

            return "Department name is changed to: " + updatedDepartment.getDepartName();
        }
    }

    /** 부서 삭제 */
    @DeleteMapping("/departments/{departNo}")
    public String deleteDepartment(int departNo) {
        departmentService.deleteDepartment(departNo);
        return "successfully deleted department";
    }

    /* 각 부서별 구성원들의 인원수 */
    public int membersInDepartments(int departNo) {
        System.out.println("departNo: " + departNo);
//        DepartmentDTO departmentInfo = departmentService.findDepartment(departNo);
        int noOfMembersInDepartment = memberService.findNoOfMembers(departNo);
        return noOfMembersInDepartment;
    }

    @GetMapping("/departmentDetails")
    public List<DepartmentDTO> showDepartmentDetails() {
        return departmentService.findDepartments();
    }
}
