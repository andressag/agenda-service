package com.andressag.agenda.user.persistence.repository;

import com.andressag.agenda.user.persistence.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("select u from UserEntity u where u.login = :login and u.password = :pwd")
    UserEntity findUser(@Param("login") String login, @Param("pwd") String pwd);

}
