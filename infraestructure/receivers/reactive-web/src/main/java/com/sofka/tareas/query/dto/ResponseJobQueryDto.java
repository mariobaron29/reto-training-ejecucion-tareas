package com.sofka.tareas.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sofka.ejecuciontareas.domain.canonical.jobexecution.JobExecutionCanonical;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseJobQueryDto {

    @JsonProperty("jobs")
    private List<JobExecutionCanonical> jobCanonical;

    @JsonProperty("error")
    private String error;


}
