package com.sofka.ejecuciontareas.configbuilder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ConfigParameters {

    private final String componentName;
    private final String serviceName;
    private final String operation;
    private final String routingKey;
    private final String source;

}
