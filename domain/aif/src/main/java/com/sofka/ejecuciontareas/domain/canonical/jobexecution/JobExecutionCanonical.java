package com.sofka.ejecuciontareas.domain.canonical.jobexecution;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobExecutionCanonical {

    private String id;
    private String jobId;
    private Date scheduledTime;
    private Date startTime;
    private Date endTime;
    private Long executionTime;
    private String httpCode;
    private String status;
    private String output;
}
