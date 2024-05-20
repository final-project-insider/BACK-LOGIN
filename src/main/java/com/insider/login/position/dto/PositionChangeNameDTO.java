package com.insider.login.position.dto;

import com.insider.login.position.entity.Position;

public class PositionChangeNameDTO {
    private String positionName;
    private String positionLevel;
    private String newPositionName;

    public PositionChangeNameDTO() {
    }

    public PositionChangeNameDTO(String positionName, String positionLevel, String newPositionName) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
        this.newPositionName = newPositionName;
    }

    public static PositionDTO mapToDTO (Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionName(position.getPositionName());
        positionDTO.setPositionLevel(position.getPositionLevel());
        return positionDTO;
    }

    public String getNewPositionName() {
        return newPositionName;
    }

    public void setNewPositionName(String newPositionName) {
        this.newPositionName = newPositionName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
                ", newPositionName='" + newPositionName + '\'' +
                '}';
    }
}
