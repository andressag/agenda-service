package com.andressag.agenda.user.resource.view;

import com.andressag.agenda.user.persistence.model.CustomerEntity;
import com.andressag.agenda.user.persistence.model.ServiceEntity;
import com.andressag.agenda.user.persistence.model.ServiceProviderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentView {

    private Long id;
    private ServiceEntity service;
    private CustomerEntity customer;
    private ServiceProviderEntity provider;
    private LocalDateTime creationDate;
    private LocalDateTime appointmentDateTime;
    private Integer duration;
}
