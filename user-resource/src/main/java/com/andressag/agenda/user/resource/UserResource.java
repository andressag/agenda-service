package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.emptyList;

@RestController
public class UserResource {

    @GetMapping
    Iterable<User> findAll() {

        return emptyList();
    }

}
