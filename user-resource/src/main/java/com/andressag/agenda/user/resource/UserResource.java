package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.exception.FindUserException;
import com.andressag.agenda.user.persistence.UserEntity;
import com.andressag.agenda.user.persistence.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    Iterable<UserView> findAll(HttpServletRequest request) {
        final List<UserView> results = new LinkedList<>();
        try {
            repository.findAll().forEach(entity -> results.add(convert(entity)));
        } catch (Exception error) {
            final FindUserException exception = new FindUserException(error);
            exception.setIpAddress(request.getRemoteAddr());
            exception.setErrorTimestamp(LocalDateTime.now());
            throw exception;
        }
        return results;
    }

    private UserView convert(UserEntity entity) {
        return UserView.builder()
                .login(entity.getLogin())
                .profiles(entity.getProfiles())
                .build();
    }

}
