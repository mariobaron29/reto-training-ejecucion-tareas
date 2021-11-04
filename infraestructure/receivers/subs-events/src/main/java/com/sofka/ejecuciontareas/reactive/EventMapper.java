package com.sofka.ejecuciontareas.reactive;


import com.sofka.ejecuciontareas.common.ObjectMapperDomain;
import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@SuppressWarnings("unchecked")
@Component
@RequiredArgsConstructor
public class EventMapper implements ObjectMapperDomain {

    private final ObjectMapper objectMapper;


    public JobEvent buildCanonicalIn(DomainEvent message) {

            return buildJobEvent((Map<String, Object>) message.getData());

    }

    public JobEvent buildJobEvent(Map<String, Object> data){
        return JobEvent.builder()
                    .jobId((String) data.get("jobId"))
                    .eventId((String) data.get("eventId"))
                    .eventName((String) data.get("eventName"))
                    .cronRegExp((String) data.get("cronRegExp"))
                    .email((String) data.get("email"))
                    .status((String) data.get("status"))
                    .timeZone((String) data.get("timeZone"))
                    .url((String) data.get("url"))
                .build();
    }
}
