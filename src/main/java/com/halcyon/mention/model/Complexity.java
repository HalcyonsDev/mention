package com.halcyon.mention.model;

import lombok.Getter;

@Getter
public enum Complexity {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private final String customName;

    Complexity(String customName) {
        this.customName = customName;
    }
}