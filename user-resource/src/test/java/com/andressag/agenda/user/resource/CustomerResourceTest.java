package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.exception.CustomerResourceException;
import com.andressag.agenda.user.persistence.model.CustomerEntity;
import com.andressag.agenda.user.persistence.repository.CustomerRepository;
import com.andressag.agenda.user.resource.view.CustomerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceTest {

    private CustomerResource resource;

    @Mock
    private CustomerRepository repository;

    @Before
    public void before() {
        this.resource = new CustomerResource(repository);
    }

    @Test
    public void testFindAll() {

        // Given
        final CustomerEntity entity = CustomerEntity.builder()
                .active(true)
                .creationDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .firstName("Peter")
                .lastName("Park")
                .build();
        when(repository.findAll()).thenReturn(Collections.singleton(entity));

        // When
        final Iterable<CustomerView> results = this.resource.findAllCustomers();

        // Then
        verify(repository).findAll();
        assertThat(results).isNotEmpty().hasSize(1);
        final CustomerView customer = results.iterator().next();
        assertThat(customer.getFirstName()).isEqualTo("Peter");
    }

    @Test
    public void testFindAllWithException() {

        // Given
        when(repository.findAll()).thenThrow(new RuntimeException("mocked"));

        // When
        try {
            this.resource.findAllCustomers();
            failBecauseExceptionWasNotThrown(CustomerResourceException.class);
        } catch (CustomerResourceException exception) {

            // Then
            verify(repository.findAll());
            assertThat(exception.getMessage()).isNotEmpty();
        }
    }
}