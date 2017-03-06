package com.andressag.agenda.user.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.time.ZoneId.systemDefault;
import static javax.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "services")
@AllArgsConstructor(access = PRIVATE)
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double price;
    private Boolean active;
    private Integer duration;
    private String description;

    @OneToMany
    private List<ServiceProviderEntity> providers;

    @Temporal(TIMESTAMP)
    private Date creationDate;

    @PrePersist
    void creationTimestamp() {
        this.creationDate = getCurrentDate();
    }

    private Date getCurrentDate() {
        return Date.from(LocalDateTime.now().atZone(systemDefault()).toInstant());
    }
}
