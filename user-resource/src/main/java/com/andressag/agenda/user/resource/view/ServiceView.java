package com.andressag.agenda.user.resource.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceView {

    private Long id;
    private String name;
    private Double price;
    private Boolean active;
    private Integer duration;
    private String description;
    private List<ServiceProviderView> providers;
}
