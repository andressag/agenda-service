package com.andressag.agenda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum Profile {
    ADMIN("Administrator"),
    SERVICE_PROVIDER("Service provider"),
    CUSTOMER("Customer");

    private final String description;
}
