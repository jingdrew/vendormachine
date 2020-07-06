package com.vm.xyz.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationUserPermission {
    APP_READ("app:read"),
    APP_WRITE("app:write");

    private final String permission;

}
