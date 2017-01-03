package com.andressag.agenda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum AgendaErrors {
    INTERNAL_ERROR(7000, "Internal system error");

    private Integer code;
    private String message;
}
