package com.alamin.jwt_advance.enums;

public enum Permission {
    CREATE_USER("Create User"),
    READ_USER( "Read User"),
    UPDATE_USER("Update User"),
    DELETE_USER("Delete User");

    private final String nameEn;
    Permission(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getNameEn() {
        return nameEn;
    }
    public static Permission fromNameEn(String nameEn) {
        for (Permission permission : Permission.values()) {
            if (permission.getNameEn().equals(nameEn)) {
                return permission;
            }
        }
        return null;
    }

}
