package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResourceTest {

    private final UserResource resource = new UserResource();

    @Test
    public void findAllShouldReturnAnEmptyList() {

        // When
        Iterable<User> result = this.resource.findAll();

        // Then
        assertThat(result).isEmpty();

    }
}