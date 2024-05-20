package com.insider.login.approval.dto;

public class PositionDTO {

    private String positionLevel;       //직급레벨
    private String positionName;        //직급이름

    public PositionDTO() {
    }

    public PositionDTO(String positionLevel, String positionName) {
        this.positionLevel = positionLevel;
        this.positionName = positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }


    @Override
    public String toString() {
        return "PositionDTO{" +
                "positionLevel='" + positionLevel + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
