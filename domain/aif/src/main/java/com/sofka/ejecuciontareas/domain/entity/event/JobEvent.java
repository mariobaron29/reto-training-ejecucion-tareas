package com.sofka.ejecuciontareas.domain.entity.event;

import com.sofka.ejecuciontareas.domain.entity.jobexecution.JobExecution;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JobEvent {
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
