package org.sosd.core.domain.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddressBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private Double eventLat;
    @NotBlank
    private Double eventLon;
    @NotBlank
    private Double userLat;
    @NotBlank
    private Double userLon;

    private Long eventId;
}
