package com.insider.login.common.utils;

public enum MemberRole {

    MEMBER("MEMBER"),
    ALL("MEMBER,ADMIN"),
    ADMIN("ADMIN");

    private String role;
    MemberRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
