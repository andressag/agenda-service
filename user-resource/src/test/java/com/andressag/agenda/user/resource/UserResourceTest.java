package com.andressag.agenda.user.resource;

import com.andressag.agenda.model.Profile;
import com.andressag.agenda.user.persistence.UserEntity;
import com.andressag.agenda.user.persistence.UserRepository;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserRepository repository;

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
        final Iterable<UserView> results = this.resource.findAll();

        // Then
        assertThat(results).isNotNull().hasSize(1);
        UserView firstUser = results.iterator().next();
        assertThat(firstUser.getLogin()).isEqualTo("rodrigo");
    }
}