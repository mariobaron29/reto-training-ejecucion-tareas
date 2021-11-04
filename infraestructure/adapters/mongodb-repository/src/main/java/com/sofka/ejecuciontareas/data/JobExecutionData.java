package com.sofka.ejecuciontareas.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JobExecutionData {
    @Id
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
