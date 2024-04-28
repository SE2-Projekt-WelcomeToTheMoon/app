package com.example.se2_projekt_app.networking.json;

import lombok.Getter;

@Getter
public enum JSONKeys {
    ACTION("action"),
    USERNAME("username"),
    SUCCESS("success"),
    MESSAGE("message"),
    ERROR("error");


    private final String value;
    JSONKeys(String action){
        this.value = action;
    }
}
