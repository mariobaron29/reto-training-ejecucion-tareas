package com.sofka.ejecuciontareas.reactive.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MessageData {

    private String id;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private String status;
}
