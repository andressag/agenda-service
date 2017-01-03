package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.Profile;
import com.andressag.agenda.user.exception.FindUserException;
import com.andressag.agenda.user.persistence.UserEntity;
import com.andressag.agenda.user.persistence.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static com.andressag.agenda.model.Profile.ADMIN;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private HttpServletRequest request;

    private UserResource resource;

    @Before
    public void before() {
        this.resource = new UserResource(repository);
    }

    @Test
    public void shouldReturnUsersSuccessfully() {

        // Given
        Set<Profile> profiles = new HashSet<>();
        profiles.add(ADMIN);
        UserEntity entity = UserEntity.builder()
                .login("rodrigo")
                .profiles(profiles)
                .build();
        when(repository.findAll()).thenReturn(singletonList(entity));

        // When
        final Iterable<UserView> results = this.resource.findAll(request);

        // Then
        assertThat(results).isNotNull().hasSize(1);
        UserView firstUser = results.iterator().next();
        assertThat(firstUser.getLogin()).isEqualTo("rodrigo");
    }

    @Test
    public void shouldWrapException() {

        // Given
        final String expectedIp = "10.0.0.1";
        when(repository.findAll()).thenThrow(new RuntimeException("mocked"));
        when(request.getRemoteAddr()).thenReturn(expectedIp);

        // When
        try {
            this.resource.findAll(request);
            failBecauseExceptionWasNotThrown(FindUserException.class);
        } catch (FindUserException exception) {

            // Then
            assertThat(exception.getIpAddress()).isEqualTo(expectedIp);
            assertThat(exception.getErrorTimestamp()).isNotNull();
        }
    }

}