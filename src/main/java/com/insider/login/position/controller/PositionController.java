package com.insider.login.position.controller;

import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.dto.PositionChangeNameDTO;
import com.insider.login.position.service.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PositionController {

    private final PositionService positionService;

    public PositionController (PositionService positionService) {
        this.positionService = positionService;
    }

    /** 직급 등록 */
    @PostMapping("/position")
    public ResponseEntity<String> registPosition(@RequestBody Map<String, String> body) {
        try {
            String newPositionName = body.get("newPositionName");
            String positionLevelInString = body.get("positionLevel");
//            int positionLevel = Integer.parseInt(positionLevelInString);
            System.out.println("inputted position name: " + newPositionName);
            System.out.println("inputted position level: " + positionLevelInString);
            PositionDTO insertNewPositionInfo = new PositionDTO();
            insertNewPositionInfo.setPositionLevel(positionLevelInString);
            insertNewPositionInfo.setPositionName(newPositionName);
            positionService.insertPosition(insertNewPositionInfo);
            return ResponseEntity.ok().body("Position successfully registered");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register new position name: " + e.getMessage());
        }
    }

    /** 직급 조회 */
    @GetMapping("/showAllPosition")
    public List<PositionDTO> showAllPositionList() {
        List<PositionDTO> positionDTOList = positionService.findAllPositionList();

        return positionDTOList;
    }

    /** 직급 삭제 */
    @DeleteMapping("/positions/{positionName}")
    public String deletePosition(@PathVariable("positionName") String positionName) {
        positionService.deletePosition(positionName);

        return "successfully deleted position";
    }

//    @PutMapping("/departmentAndPosition/{positionName}")
//    public ResponseEntity<String> updateDepartName(@PathVariable("positionName") String positionName, @RequestBody PositionDTO positionDTO) {
//        // 변경할 부서 정보 가져오기
//        PositionDTO foundPosition = getSpecificPosition(positionName);
//        // 덮어씌우고
//        foundPosition.setPositionName(positionDTO.getNewPositionName());
//
////        PositionDTO checkExistingPositionName = getSpecificPosition(foundPosition.getPositionName());
//        positionService.updatePositionName(foundPosition);
//        return ResponseEntity.ok("직급명이 성공적으로 변경되었습니다");
//
////        if (checkExistingPositionName != null) {
////            return ResponseEntity.status(HttpStatus.CONFLICT).body("직급명이 존재합니다");
////        } else {
//    }

    @PutMapping("/position/{positionName}")
    public ResponseEntity<String> updatePositionName(@PathVariable("positionName") String positionName, @RequestBody PositionChangeNameDTO inputtedPositionNameDTO) {
        try {
            /* 기존에 있는 직급 이름 */
            System.out.println("position name to be changed: " + positionName);

            /* 새로 입력한 직급명 */
            String inputtedPositionName = inputtedPositionNameDTO.getNewPositionName();
            System.out.println("inputted new position name: " + inputtedPositionName);

            /* 직급 레벨 가져오는 logic */
//            PositionDTO positionToBeAltered = getSpecificPosition(positionName);
//            System.out.println("positionToBeAltered: " + positionToBeAltered);

            /* 입력한 변경할 직급명이 존재하는지 확인하는 logic */
            List<PositionDTO> listOfPositions = positionService.findPositionName();

            boolean positionNameExists = listOfPositions.stream().map(PositionDTO::getPositionName).anyMatch(name -> name.equals(inputtedPositionName));
            if (!positionNameExists) {
                System.out.println("new position name doesn't exist, updating...");
//                positionService.updatePositionName(positionToBeAltered, inputtedPositionName);
                positionService.updatePositionName(positionName, inputtedPositionName);
                return ResponseEntity.ok().body("Position name has been successfully updated");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Position name already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update position name: " + e.getMessage());
        }
    }


    public PositionDTO getSpecificPosition(String positionName) {
        PositionDTO foundPosition = positionService.findSpecificPosition(positionName);
        System.out.println("foundPosition: " + foundPosition);
        return foundPosition;
    }

}