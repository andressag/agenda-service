package com.andressag.agenda.user.persistence.repository;

import com.andressag.agenda.user.persistence.model.ServiceProviderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRepository extends CrudRepository<ServiceProviderEntity, Long> {
}
