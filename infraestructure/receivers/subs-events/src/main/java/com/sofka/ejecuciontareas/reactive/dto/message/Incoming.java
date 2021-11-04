package com.sofka.ejecuciontareas.reactive.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Incoming {

    private String eventName;
    private String eventId;
    private EventMessage data;
}


