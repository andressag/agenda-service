package com.andressag.agenda.user.persistence;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityTest {

    @Test
    public void creationTimestampShouldSetBothTimestamps() {

        // When
        UserEntity entity = UserEntity.builder().build();
        entity.creationTimestamp();

        // Then
        assertThat(entity.getCreationDate()).isNotNull();
        assertThat(entity.getUpdateDate()).isNotNull().isEqualTo(entity.getCreationDate());
    }

    @Test
    public void updateTimestampShouldBeGreaterThanCreation() throws InterruptedException {

        // When
        UserEntity entity = UserEntity.builder().build();
        entity.creationTimestamp();
        Thread.sleep(10);
        entity.updateTimestamp();

        // Then
        assertThat(entity.getUpdateDate()).isNotNull().isAfter(entity.getCreationDate());
    }


}