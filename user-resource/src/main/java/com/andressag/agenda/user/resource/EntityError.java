package com.andressag.agenda.user.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EntityError {

    private Integer errorCode;
    private String errorDetails;
}
