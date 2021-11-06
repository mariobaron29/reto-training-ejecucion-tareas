package com.sofka.ejecuciontareas.domain.response;

import com.sofka.ejecuciontareas.domain.canonical.jobexecution.JobExecutionCanonical;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class JobResponse {

    private final List<JobExecutionCanonical> jobExecutionCanonical;

}
