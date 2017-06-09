package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.exception.UserResourceException;
import com.andressag.agenda.user.persistence.model.UserEntity;
import com.andressag.agenda.user.persistence.repository.UserRepository;
import com.andressag.agenda.user.resource.view.UserView;
import lombok.AllArgsConstructor;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserResource {

    private final UserRepository repository;

    @GetMapping
    Iterable<UserView> findAll(HttpServletRequest request) {
        final List<UserView> results = new LinkedList<>();
        try {
            repository.findAll().forEach(entity -> results.add(convert(entity)));
        } catch (Exception error) {
            propagateException(request, error);
        }
        return results;
    }

    @GetMapping(path = "/{code}")
    UserView findUserById(@PathVariable("code") Long code, HttpServletRequest request) {
        UserView result = null;
        try {
            result = convert(repository.findOne(code));
        } catch (Exception error) {
            propagateException(request, error);
        }
        return result;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    UserView createUser(@RequestBody UserEntity user, HttpServletRequest request) {
        UserView saved = null;
        try {
            saved = convert(repository.save(user));
        }  catch (Exception error) {
            propagateException(request, error);
        }
        return saved;
    }

    @PutMapping(path = "/{code}")
    UserView updateUser(@RequestBody UserEntity user, HttpServletRequest request) {
        UserView updated = null;
        try {
            updated = convert(repository.save(user));
        }  catch (Exception error) {
            propagateException(request, error);
        }
        return updated;
    }

    @DeleteMapping(path = "/{code}")
    ResponseEntity<Void> deleteUser(@PathParam("code") Long code, HttpServletRequest request) {
        try {
            repository.delete(code);
        } catch (Exception error) {
            propagateException(request, error);
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping(path = "/authenticate")
    ResponseEntity<UserEntity> authenticate(@RequestHeader("Authorization") String authorizationHeader) {

        String[] headerValue = decodeRequestHeader(authorizationHeader);
        if (nonNull(headerValue)) {
            String login = headerValue[0];
            String pwd = headerValue[1];

            UserEntity user = findUser(login, pwd);
            if (nonNull(user)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(user);
            }
        }
        return ResponseEntity.status(UNAUTHORIZED)
                .body(null);
    }

    private UserEntity findUser(String login, String pwd) {
        UserEntity user;
        try {
            user = repository.findUser(login, pwd);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    private String[] decodeRequestHeader(String header) {
        if (nonNull(header) && header.startsWith("Basic ")) {
            byte[] decodeHeader;
            try {
                final String[] tokens = header.split("Basic ");
                decodeHeader = Base64.getDecoder().decode(tokens[1].getBytes());
            } catch (Exception error) {
                return null;
            }
            return new String(decodeHeader).split(":");
        }
        return null;
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
