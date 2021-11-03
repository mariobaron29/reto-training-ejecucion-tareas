package com.sofka.ejecuciontareas.domain.canonical;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobCanonical {

    private String id;
    private String url;
    private String timeZone;
    private String email;
    private String cronRegExp;
    private String status;

}
