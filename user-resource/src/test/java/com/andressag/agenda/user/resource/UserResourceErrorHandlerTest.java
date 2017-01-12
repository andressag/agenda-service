package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.exception.UserResourceException;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static com.andressag.agenda.model.AgendaErrors.INTERNAL_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class UserResourceErrorHandlerTest {

    @Test
    public void shouldReturnResponseEntity() {

        // Given
        final UserResourceException exception = new UserResourceException(new Exception());

        // When
        final ResponseEntity<UserError> results = new UserResourceErrorHandler().findUserException(exception);

        // Then
        assertThat(results).isNotNull();
        assertThat(results.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(results.getBody()).isNotNull();
        assertThat(results.getBody().getErrorCode()).isEqualTo(INTERNAL_ERROR.getCode());
        assertThat(results.getBody().getErrorDetails()).isEqualTo(INTERNAL_ERROR.getMessage());
    }

}