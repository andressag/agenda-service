package com.andressag.agenda.user.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserError {

    private Integer errorCode;
    private String errorDetails;
}
