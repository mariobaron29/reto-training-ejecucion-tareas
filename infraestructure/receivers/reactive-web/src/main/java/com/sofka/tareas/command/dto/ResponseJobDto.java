package com.sofka.tareas.command.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sofka.ejecuciontareas.domain.canonical.event.JobEventCanonical;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseJobDto {

    @JsonProperty("job")
    private JobEventCanonical jobEventCanonical;

    @JsonProperty("error")
    private String error;


}
