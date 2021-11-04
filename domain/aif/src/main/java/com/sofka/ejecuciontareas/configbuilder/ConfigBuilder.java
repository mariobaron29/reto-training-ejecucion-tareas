package com.sofka.ejecuciontareas.configbuilder;

import com.sofka.ejecuciontareas.common.event.EventsGateway;
import com.sofka.ejecuciontareas.domain.canonical.jobexecution.JobExecutionRepository;
import com.sofka.ejecuciontareas.domain.canonical.event.EventCanonicalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ConfigBuilder {

    private final EventsGateway eventsGateway;
    private final JobExecutionRepository jobExecutionRepository;
    private final EventCanonicalRepository eventRepository;
    private final ConfigParameters configParameters;
}
