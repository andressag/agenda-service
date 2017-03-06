package com.andressag.agenda.user.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneId.systemDefault;
import static javax.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "appointments")
@AllArgsConstructor(access = PRIVATE)
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private ServiceEntity service;

    @OneToOne
    private CustomerEntity customer;

    @OneToOne
    private ServiceProviderEntity provider;

    @Temporal(TIMESTAMP)
    private Date creationDate;

    @Temporal(TIMESTAMP)
    private Date appointmentDateTime;

    private Integer duration;

    @PrePersist
    void creationTimestamp() {
        this.creationDate = getCurrentDate();
    }

    private Date getCurrentDate() {
        return Date.from(LocalDateTime.now().atZone(systemDefault()).toInstant());
    }
}
