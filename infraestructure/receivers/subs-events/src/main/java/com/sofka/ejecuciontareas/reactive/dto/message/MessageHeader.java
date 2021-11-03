package com.sofka.ejecuciontareas.reactive.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MessageHeader {

    private String transactionId;
    private String serviceName;
    private String transactionDate;
    private String messageErrorMapper;

}
