package com.andressag.agenda.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
public class User {

    private final String login;
    private final String password;
    private final Set<Profile> profiles;
    private final LocalDate creationDate;
}
