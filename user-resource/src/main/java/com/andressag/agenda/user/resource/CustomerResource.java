package com.andressag.agenda.user.resource;

import com.andressag.agenda.user.exception.CustomerResourceException;
import com.andressag.agenda.user.persistence.model.CustomerEntity;
import com.andressag.agenda.user.persistence.repository.CustomerRepository;
import com.andressag.agenda.user.resource.view.CustomerView;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static java.time.ZoneId.systemDefault;

@RestController
@RequestMapping(path = "/customers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerResource {

    private final CustomerRepository repository;

    @GetMapping
    public List<CustomerView> findAllCustomers() {
        final List<CustomerView> results = new LinkedList<>();
        try {
            repository.findAll().forEach(entity -> results.add(convert(entity)));
            return results;
        } catch (Exception error) {
            throw new CustomerResourceException(error);
        }
    }

    private CustomerView convert(CustomerEntity entity) {
        return CustomerView.builder()
                .active(entity.getActive())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .creationDate(entity.getCreationDate().toInstant().atZone(systemDefault()).toLocalDateTime())
                .id(entity.getId())
                .build();
    }

}
