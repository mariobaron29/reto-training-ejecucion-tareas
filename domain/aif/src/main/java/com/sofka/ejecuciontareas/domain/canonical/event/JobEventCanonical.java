package com.sofka.ejecuciontareas.domain.canonical.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobEventCanonical {

    private String eventId;
    private String eventName;

    private String jobId;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private Boolean status;

    private JobExecution jobExecution;

}
