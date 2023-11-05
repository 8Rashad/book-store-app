package com.example.bookstoring.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationUserPermission {
    STUDENT_CURRENTLY_READ("student:read"),
    STUDENT_VIEW_ALL_BOOKS("student:view"),
    AUTHOR_CREATE_BOOKS("author:create"),
    AUTHOR_DELETE_BOOKS("author:delete");

    private final String permission;

    public String getPermission() {
        return permission;
    }
}
