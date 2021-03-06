package com.andressag.agenda.user.resource.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerView {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean active;
    private LocalDateTime creationDate;
}
