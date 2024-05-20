package com.insider.login.department.controller;

import com.insider.login.department.dto.DepartmentChangeNameDTO;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.dto.DepartmentDetailsDTO;
import com.insider.login.department.entity.Department;
import com.insider.login.department.service.DepartmentService;
import com.insider.login.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final MemberService memberService;

    public DepartmentController (DepartmentService departmentService, MemberService memberService) {
        this.departmentService = departmentService;
        this.memberService = memberService;
    }

    /** 특정 부서 가져오기 */
//    @GetMapping("getDepartByName")
////    public ResponseEntity<Integer> getSpecificDepartment(@RequestBody Map<String, String> body) {
//    public ResponseEntity<Integer> getSpecificDepartment(@RequestParam String departName) {
//        try {
////            String departName = body.get("departName");
//            System.out.println("삭제할 부서명: " + departName);
//            Department department = departmentService.findDepartByName(departName);
//            return ResponseEntity.ok(department.getDepartNo());
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

//    @GetMapping("/getDepartByName")
//    public ResponseEntity<Integer> getSpecificDepartment(@RequestParam String departName) {
//        try {
//            System.out.println("삭제할 부서명: " + departName);
//            Department department = departmentService.findDepartByName(departName);
//            return ResponseEntity.ok(department.getDepartNo());
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

    @GetMapping("/departmentAndPosition")
    public ResponseEntity<Integer> getSpecificDepartment(@RequestParam String departName) {
        try {
            System.out.println("삭제할 부서명: " + departName);
            Department department = departmentService.findDepartByName(departName);
            return ResponseEntity.ok(department.getDepartNo());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /** 부서 등록 */
    @PostMapping("/departments")
    public ResponseEntity<String> insertDepartment(@RequestBody Map<String, String> body) {
        try {
            String newDepartName = body.get("newDepartName");
            System.out.println("입력 받은 newDepartName: " + newDepartName);
            DepartmentDTO insertDepartment = new DepartmentDTO();
            insertDepartment.setDepartName(newDepartName);
            departmentService.insertDepartment(insertDepartment);
            return ResponseEntity.ok().body("Department successfully registered");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register new department name: " + e.getMessage());
        }
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
        System.out.println("deparmentDetailsDTOList: " + departmentDetailsDTOList);
        return departmentDetailsDTOList;
    }

    /** 부서 수정 */
    @PutMapping("/departments/{departNo}")
    public ResponseEntity<String> updateDepartName(@PathVariable("departNo") int departNo, @RequestBody DepartmentChangeNameDTO inputtedDepartName) {
        try {
            System.out.println("departNo to be updated: " + departNo);
            System.out.println("inputted department name: " + inputtedDepartName.getNewDepartName());
            String inputDepartName = inputtedDepartName.getNewDepartName();

            List<DepartmentDTO> listOfDepartments = departmentService.findDepartments();

            boolean departNameExists = listOfDepartments.stream().map(DepartmentDTO::getDepartName).anyMatch(name -> name.equals(inputDepartName));
            if (!departNameExists) {
                System.out.println("new department name doesn't exist, updating...");
                departmentService.updateDepartmentName(departNo, inputDepartName);
                return ResponseEntity.ok().body("Department name has been successfully updated");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Department name already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update department name: " + e.getMessage());
        }
    }
//        inputtedDepartName.setDepartNo(departNo);
//        String existingDepartName = departmentService.findDepartName(departNo);
//
//        /* 부서명이 이미 존재한다면 */
//        if (existingDepartName.equals(inputtedDepartName.getDepartName())) {
//            return "Department Name already exists";
//        } else {
//            DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO);
//
//            return "Department name is changed to: " + updatedDepartment.getDepartName();
//        }
//    }

    /** 부서 삭제 */
    @DeleteMapping("/departments/{departNo}")
    public String deleteDepartment(@PathVariable("departNo") int departNo) {
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
