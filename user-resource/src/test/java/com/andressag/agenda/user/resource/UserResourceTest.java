package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.Profile;
import com.andressag.agenda.user.exception.UserResourceException;
import com.andressag.agenda.user.persistence.UserEntity;
import com.andressag.agenda.user.persistence.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static com.andressag.agenda.model.Profile.CUSTOMER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    private UserResource resource;

    @Mock
    private UserRepository repository;

    @Mock
    private HttpServletRequest request;

    @Before
    public void before() {
        this.resource = new UserResource(repository);
    }

    @Test
    public void testFindAll() {

        // Given
        UserEntity entity = UserEntity.builder()
                .login("rodrigo")
                .build();
        when(repository.findAll()).thenReturn(singletonList(entity));

        // When
        final Iterable<UserView> results = this.resource.findAll(request);

        // Then
        verify(repository).findAll();
        assertThat(results).isNotNull().hasSize(1);
        UserView firstUser = results.iterator().next();
        assertThat(firstUser.getLogin()).isEqualTo("rodrigo");
    }

    @Test
    public void testFindAllWithException() {

        // Given
        final String expectedIp = "10.0.0.1";
        when(repository.findAll()).thenThrow(new RuntimeException("mocked"));
        when(request.getRemoteAddr()).thenReturn(expectedIp);

        // When
        try {
            this.resource.findAll(request);
            failBecauseExceptionWasNotThrown(UserResourceException.class);
        } catch (UserResourceException exception) {

            // Then
            verify(repository).findAll();
            assertThat(exception.getIpAddress()).isEqualTo(expectedIp);
            assertThat(exception.getErrorTimestamp()).isNotNull();
        }
    }

    @Test
    public void testFindUserById() {

        // Given
        Long userId = 12345L;
        UserEntity entity = UserEntity.builder()
                .id(userId)
                .login("rodrigo")
                .build();
        when(repository.findOne(userId)).thenReturn(entity);

        // When
        final UserView result = this.resource.findUserById(userId, request);

        // Then
        verify(repository).findOne(eq(userId));
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("rodrigo");
    }

    @Test
    public void testFindUserByIdWithException() {

        // Given
        final String expectedIp = "10.0.0.1";
        Long userId = 12345L;
        when(repository.findOne(userId)).thenThrow(new RuntimeException("mocked"));
        when(request.getRemoteAddr()).thenReturn(expectedIp);

        // When
        try {
            this.resource.findUserById(userId, request);
            failBecauseExceptionWasNotThrown(UserResourceException.class);
        } catch (UserResourceException exception) {

            // Then
            verify(repository).findOne(eq(userId));
            assertThat(exception.getIpAddress()).isEqualTo(expectedIp);
            assertThat(exception.getErrorTimestamp()).isNotNull();
        }
    }

    @Test
    public void testCreateUser() {

        // Given
        final Set<Profile> profiles = new HashSet<>();
        profiles.add(CUSTOMER);
        final UserEntity entity = UserEntity.builder()
                .login("garou")
                .profiles(profiles)
                .password("1234556")
                .build();
        when(repository.save(eq(entity))).thenReturn(entity);

        // When
        final UserView result = this.resource.createUser(entity, request);

        // Then
        verify(repository).save(eq(entity));
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("garou");
    }

    @Test
    public void testCreateUserWithException() {

        /// Given
        final String expectedIp = "10.0.0.1";
        final Set<Profile> profiles = new HashSet<>();
        profiles.add(CUSTOMER);
        final UserEntity entity = UserEntity.builder()
                .login("garou")
                .profiles(profiles)
                .password("1234556")
                .build();
        when(repository.save(eq(entity))).thenThrow(new RuntimeException("mocked"));

        // When
        try {
            this.resource.createUser(entity, request);
            failBecauseExceptionWasNotThrown(UserResourceException.class);
        } catch (UserResourceException exception) {

            // Then
            verify(repository.save(eq(entity)));
            assertThat(exception.getIpAddress()).isEqualTo(expectedIp);
            assertThat(exception.getErrorTimestamp()).isNotNull();
        }
    }

    @Test
    public void testUpdateUser() {

        // Given
        final Set<Profile> profiles = new HashSet<>();
        profiles.add(CUSTOMER);
        final UserEntity entity = UserEntity.builder()
                .login("garou")
                .profiles(profiles)
                .password("123****")
                .build();
        when(repository.save(eq(entity))).thenReturn(entity);

        // When
        final UserView result = this.resource.updateUser(entity, request);

        // Then
        verify(repository).save(eq(entity));
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("garou");
    }

    @Test
    public void testUpdateUserWithException() {

        /// Given
        final String expectedIp = "10.0.0.1";
        final Set<Profile> profiles = new HashSet<>();
        profiles.add(CUSTOMER);
        final UserEntity entity = UserEntity.builder()
                .login("garou")
                .profiles(profiles)
                .password("1234556")
                .build();
        when(repository.save(eq(entity))).thenThrow(new RuntimeException("mocked"));

        // When
        try {
            this.resource.updateUser(entity, request);
            failBecauseExceptionWasNotThrown(UserResourceException.class);
        } catch (UserResourceException exception) {

            // Then
            verify(repository.save(eq(entity)));
            assertThat(exception.getIpAddress()).isEqualTo(expectedIp);
            assertThat(exception.getErrorTimestamp()).isNotNull();
        }
    }

    @Test
    public void testDeleteUser() {

        // Given
        Long userId = 12345L;

        // When
        final ResponseEntity<Void> response = this.resource.deleteUser(userId, request);

        // Then
        verify(repository).delete(eq(userId));
        assertThat(response).isNotNull();
    }

    @Test
    public void testDeleteUserWithException() {

        // Given
        final String expectedIp = "10.0.0.1";
        Long userId = 12345L;

        // When
        try {
            this.resource.deleteUser(userId, request);
            failBecauseExceptionWasNotThrown(UserResourceException.class);
        } catch (UserResourceException exception) {

            // Then
            verify(repository).delete(eq(userId));
            assertThat(exception.getIpAddress()).isEqualTo(expectedIp);
            assertThat(exception.getErrorTimestamp()).isNotNull();
        }
    }

}