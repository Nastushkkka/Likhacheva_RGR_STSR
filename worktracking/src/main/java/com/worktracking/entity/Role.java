package com.worktracking.entity;

public enum Role {
    GUEST("GUEST"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromString(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Передана пустая роль!");
        }
        for (Role role : Role.values()) {
            if (role.value.equals(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Нет такого значения в Role: " + text);
    }

    @Override
    public String toString() {
        return value;
    }
}
