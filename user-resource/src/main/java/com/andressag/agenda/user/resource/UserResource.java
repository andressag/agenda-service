package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.exception.UserResourceException;
import com.andressag.agenda.user.persistence.UserEntity;
import com.andressag.agenda.user.persistence.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
class UserResource {

    private final UserRepository repository;

    @Autowired
    UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/users")
    Iterable<UserView> findAll(HttpServletRequest request) {
        final List<UserView> results = new LinkedList<>();
        try {
            repository.findAll().forEach(entity -> results.add(convert(entity)));
        } catch (Exception error) {
            propagateException(request, error);
        }
        return results;
    }

    @GetMapping(path = "/users/{code}")
    UserView findUserById(@PathVariable("code") Long code, HttpServletRequest request) {
        UserView result = null;
        try {
            result = convert(repository.findOne(code));
        } catch (Exception error) {
            propagateException(request, error);
        }
        return result;
    }

    @PostMapping(path = "/users")
    @ResponseStatus
    UserView createUser(@RequestBody UserEntity user, HttpServletRequest request) {
        UserView saved = null;
        try {
            saved = convert(repository.save(user));
        }  catch (Exception error) {
            propagateException(request, error);
        }
        return saved;
    }

    @PutMapping(path = "/users/{code}")
    UserView updateUser(@RequestBody UserEntity user, HttpServletRequest request) {
        UserView updated = null;
        try {
            updated = convert(repository.save(user));
        }  catch (Exception error) {
            propagateException(request, error);
        }
        return updated;
    }

    @DeleteMapping(path = "/users/{code}")
    @ResponseStatus
    ResponseEntity<Void> deleteUser(@PathParam("code") Long code, HttpServletRequest request) {
        try {
            repository.delete(code);
        } catch (Exception error) {
            propagateException(request, error);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private void propagateException(HttpServletRequest request, Exception error) {
        final UserResourceException exception = new UserResourceException(error);
        exception.setIpAddress(request.getRemoteAddr());
        exception.setErrorTimestamp(LocalDateTime.now());
        throw exception;
    }

    private UserView convert(UserEntity entity) {
        return UserView.builder()
                .login(entity.getLogin())
                .profiles(entity.getProfiles())
                .build();
    }
}
