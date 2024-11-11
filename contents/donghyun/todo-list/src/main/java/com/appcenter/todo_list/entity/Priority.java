package com.appcenter.todo_list.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Priority {
    TOP, MIDDLE, BOTTOM;

    @JsonCreator
    public static Priority parsing(String inputValue) {
        return Stream.of(Priority.values())
                .filter(priority -> priority.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
