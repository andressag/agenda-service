package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.persistence.UserEntity;
import com.andressag.agenda.user.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
class UserResource {

    private final UserRepository repository;

    @Autowired
    UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Iterable<UserView> findAll() {
        final List<UserView> results = new LinkedList<>();
        repository.findAll().forEach(entity -> results.add(convert(entity)));
        return results;
    }

    private UserView convert(UserEntity entity) {
        return UserView.builder()
                .login(entity.getLogin())
                .profiles(entity.getProfiles())
                .build();
    }

}
