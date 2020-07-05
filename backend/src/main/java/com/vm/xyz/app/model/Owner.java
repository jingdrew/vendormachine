package com.vm.xyz.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Owner {
    CLIENT("CLIENT"),
    MACHINE("MACHINE");

    private final String owner;
}
