package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.Profile;
import com.andressag.agenda.user.persistence.model.UserEntity;
import com.andressag.agenda.user.persistence.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.andressag.agenda.model.Profile.ADMIN;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserRepository repository;

    @Test
    public void shouldReturnSuccessfully() {

        // Given
        Set<Profile> profiles = new HashSet<>();
        profiles.add(ADMIN);
        UserEntity entity = UserEntity.builder()
                .login("admin")
                .profiles(profiles)
                .build();
        given(repository.findAll()).willReturn(singletonList(entity));

        // When
        final ResponseEntity<List> response = restTemplate.getForEntity("/users", List.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldReturnFindUserError() {

        // Given
        given(repository.findAll()).willThrow(RuntimeException.class);

        // When
        final ResponseEntity<EntityError> response = restTemplate.getForEntity("/users", EntityError.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrorCode()).isEqualTo(7000);

    }
}
