package com.andressag.agenda.user.resource.view;

import com.andressag.agenda.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

    private String login;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private Set<Profile> profiles;
}
