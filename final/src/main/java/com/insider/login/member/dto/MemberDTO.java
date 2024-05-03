package com.insider.login.member.dto;

import com.insider.login.auth.image.entity.Image;
import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.entity.Department;
import com.insider.login.position.entity.Position;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {


        private int memberId;
        private String name;
        private String password;
        //    @Column(name = "depart_no", nullable = false)
//    private int departNo;
//    @Column(name = "position_name" ,nullable = false)
//    private String positionName;
        private LocalDate employedDate;
        private String address;
        private String phoneNo;
        private String memberStatus;
        private String email;
        private MemberRole role;
        //    private com.insider.prefinal.common.UserRole userRole;
//    @Column(name = "member_image_no")
//    private int memberImageNo;
        private Department department;
        private Position position;
        private Image image;


        public List<String> getRoleList() {
            if (this.role.getRole().length() > 0) {
                return Arrays.asList(this.role.getRole().split(","));
            }
            return new ArrayList<>();
        }

        public int getMemberId() {
            return memberId;
        }


}
