package com.insider.login.position.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.access.prepost.PreAuthorize;

@Entity
@Table(name = "position_info")
@PreAuthorize("hasAuthority(ADMIN)")
public class Position {

    @Id
    @Column(name = "position_level", nullable = false, unique = true)
    private String positionLevel;

    @Column(name = "position_name", nullable = false, unique = true)
    private String positionName;

    protected Position() {
    }

    public Position(String positionName, String positionLevel) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

