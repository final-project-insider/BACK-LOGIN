package com.insider.login.position.entity;

import com.insider.login.member.entity.Member;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "position_info")
public class Position {

    @Id
    @Column(name = "position_name", nullable = false)
    private String positionName;
    @Column(name = "position_level", nullable = false)
    private String positionLevel;

//    @OneToMany(mappedBy = "position")
//    private List<Member> members;

    public Position() {
    }

    public Position(String positionName, String positionLevel, List<Member> members) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
//        this.members = members;
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

//    public List<Member> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<Member> members) {
//        this.members = members;
//    }

    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
//                ", members=" + members +
                '}';
    }
}
