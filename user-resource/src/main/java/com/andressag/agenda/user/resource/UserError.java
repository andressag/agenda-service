package com.andressag.agenda.user.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class UserError {

    private Integer errorCode;
    private String errorDetails;
}
