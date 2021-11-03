package com.sofka.ejecuciontareas.configbuilder;

import com.sofka.ejecuciontareas.common.event.EventsGateway;
import com.sofka.ejecuciontareas.domain.canonical.JobCanonicalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ConfigBuilder {

    private final EventsGateway eventsGateway;
    private final JobCanonicalRepository jobRepository;
    private final ConfigParameters configParameters;
}
