package com.andressag.agenda.user.persistence.repository;

import com.andressag.agenda.user.persistence.model.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
}
