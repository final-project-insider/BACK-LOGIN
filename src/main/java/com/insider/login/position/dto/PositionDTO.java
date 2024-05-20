package com.insider.login.position.dto;
import com.insider.login.position.entity.Position;

public class PositionDTO {

    private String positionName;
    private String positionLevel;

    public PositionDTO() {
    }

    public PositionDTO(String positionName, String positionLevel) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
    }

    public static PositionDTO mapToDTO (Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionName(position.getPositionName());
        positionDTO.setPositionLevel(position.getPositionLevel());
        return positionDTO;
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
                '}';
    }
}
