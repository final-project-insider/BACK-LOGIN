package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="ApprovalPosition")
@Table(name="POSITION_INFO")
public class Position {

    @Id
    @Column(name="position_level")
    private String positionLevel;       //직급레벨

    @Column(name="position_name")
    private String positionName;        //직급이름


    protected Position(){}

    public Position(String positionName, String positionLevel) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
                '}';
    }
}
