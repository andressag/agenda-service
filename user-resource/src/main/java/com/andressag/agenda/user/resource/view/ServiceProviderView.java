package com.andressag.agenda.user.resource.view;

import com.andressag.agenda.user.persistence.model.ServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderView {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean active;
    private List<ServiceEntity> services;
    private LocalDateTime creationDate;
}
