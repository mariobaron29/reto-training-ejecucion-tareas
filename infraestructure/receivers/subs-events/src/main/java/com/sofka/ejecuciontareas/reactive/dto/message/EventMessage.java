package com.sofka.ejecuciontareas.reactive.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EventMessage {

    private MessageHeader header;
    private MessageData messageData;
}
