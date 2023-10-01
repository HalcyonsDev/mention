package com.halcyon.mention.model;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("pending"),
    POSTPONED("postponed"),
    DONE("done"),
    FAILED("failed");

    private final String customName;

    Status(String customName) {
        this.customName = customName;
    }

}