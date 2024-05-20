package com.insider.login.position.service;

import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.entity.Position;
import com.insider.login.position.repository.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {

    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;

    public PositionService (ModelMapper modelMapper, PositionRepository positionRepository) {
        this.modelMapper = modelMapper;
        this.positionRepository = positionRepository;
    }
    @Transactional
    public void insertPosition(PositionDTO positionDTO) {
        Position position = modelMapper.map(positionDTO, Position.class);
        System.out.println("확인용: " + position);
        positionRepository.save(position);
    }

    public List<PositionDTO> findAllPositionList() {
        List<Position> positionList = positionRepository.findAll();
        Type listType = new TypeToken<List<PositionDTO>>() {}.getType();
        List<PositionDTO> departmentList = modelMapper.map(positionList, listType);

        return departmentList;
    }

    @Transactional
    public void deletePosition(String positionName) {
        System.out.println("delete position name: " + positionName);
        positionRepository.deletePositionByPositionName(positionName);
    }

    public PositionDTO findSpecificPosition(String positionName) {
        Position gotSpecificPosition = positionRepository.findByPositionName(positionName).orElse(null);
        PositionDTO foundPosition = modelMapper.map(gotSpecificPosition, PositionDTO.class);

        return foundPosition;
    }

//    @Transactional
//    public void updatePositionName(PositionDTO foundPosition, String inputtedPositionName) {
//        try {
//            System.out.println("변경할 positionName 정보: " + foundPosition.getPositionName());
//
//            // Retrieve the existing position entity from the database
//            Position currentPosition = positionRepository.findById(foundPosition.getPositionName())
//                    .orElseThrow(() -> new EntityNotFoundException("Position not found: " + foundPosition.getPositionName()));
//
//            // Update the position entity with the new data
//            currentPosition.setPositionName(inputtedPositionName);
//
//            // Save the updated position entity
//            Position savedPositionData = positionRepository.save(currentPosition);
//
//            System.out.println("변경된 position 저장 완료: " + savedPositionData);
//        } catch (Exception e) {
//            // Handle exceptions
//            System.out.println("Failed to update position name: " + e.getMessage());
//            throw new RuntimeException("Failed to updateeeeeee position name ah", e);
//        }
//    }

    @Transactional
    public void updatePositionName(String existingPositionName, String inputtedPositionName) {
        try {
            System.out.println("변경할 직급명: " + existingPositionName);

            // Retrieve the existing position entity from the database
//            Position currentPosition = positionRepository.findById(existingPositionName)
//                    .orElseThrow(() -> new EntityNotFoundException("Position not found: " + existingPositionName));
            Position currentPosition = positionRepository.findByPositionName(existingPositionName)
                    .orElseThrow(() -> new EntityNotFoundException("Position not found: " + existingPositionName));

            System.out.println("current position: " + currentPosition);

            // Update the position entity with the new data
            currentPosition.setPositionName(inputtedPositionName);

            // Save the updated position entity
            Position savedPositionData = positionRepository.save(currentPosition);

            System.out.println("변경된 position 저장 완료: " + savedPositionData);
        } catch (Exception e) {
            // Handle exceptions
            System.out.println("Failed to update position name: " + e.getMessage());
            throw new RuntimeException("Failed to updateeeeeee position name ah", e);
        }
    }

    public List<PositionDTO> findAll() {
        List<Position> positionList = positionRepository.findAll();
        List<PositionDTO> positionDTOList = Collections.singletonList(modelMapper.map(positionList, PositionDTO.class));
        return positionDTOList;
    }

    public List<PositionDTO> findPositionName() {
        List<Position> positionLists = positionRepository.findAll();
        return positionLists.stream().map(this::mapToPositionDTO).collect(Collectors.toList());
    }

    private PositionDTO mapToPositionDTO(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionName(position.getPositionName());

        return positionDTO;

    }
}
