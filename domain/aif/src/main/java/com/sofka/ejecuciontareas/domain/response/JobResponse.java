package com.sofka.ejecuciontareas.domain.response;

import com.sofka.ejecuciontareas.domain.canonical.JobCanonical;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JobResponse {

    private final JobCanonical jobCanonical;

}
