package com.appcenter.todo_list.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum Status {
    PENDING, PROGRESS, COMPLETED;

    @JsonCreator
    public static Status parsing(String inputValue) {
        return Stream.of(Status.values())
                .filter(status -> status.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
