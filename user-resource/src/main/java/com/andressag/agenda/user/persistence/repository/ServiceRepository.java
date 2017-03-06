package com.andressag.agenda.user.persistence.repository;

import com.andressag.agenda.user.persistence.model.ServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<ServiceEntity, Long> {
}
