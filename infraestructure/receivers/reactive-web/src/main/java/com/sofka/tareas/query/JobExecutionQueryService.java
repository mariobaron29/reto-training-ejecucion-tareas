package com.sofka.tareas.query;

import com.sofka.ejecuciontareas.domain.response.JobResponse;
import com.sofka.ejecuciontareas.query.JobExecutionQueryController;
import com.sofka.tareas.query.dto.ResponseJobQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class JobExecutionQueryService {

    private final JobExecutionQueryController controller;

        @GetMapping(path ="/api/v1/ejecuciones/consultar-ejecucion")
    public ResponseEntity<Mono<ResponseJobQueryDto>> findJobExecutionById(
            @RequestParam("id") String id ){

            try{
            return ResponseEntity.ok()
                    .body(controller.processFindJobExecutionById(id)
                        .flatMap(substituteResponse -> buildDto(substituteResponse))
                        );
            }catch(Exception exc){
                return handleErrors(exc);
            }
    }

    @GetMapping(path ="/api/v1/ejecuciones/consultar-ejecucion-por-tarea")
    public ResponseEntity<Mono<ResponseJobQueryDto>> findJobExecutionByJobId(
            @RequestParam("id") String id ){

            try{
            return ResponseEntity.ok()
                    .body(controller.processFindJobExecutionByJobId(id)
                        .flatMap(substituteResponse -> buildDto(substituteResponse))
                        );
            }catch(Exception exc){
                return handleErrors(exc);
            }
    }

    @GetMapping(path ="/api/v1/ejecuciones/consultar-ejecuciones")
    public ResponseEntity findAllJobExecutions( ){

             return ResponseEntity.ok()
                    .body(controller.processFindAllJobExecutions());
    }

        @GetMapping(path ="/api/v1/ejecuciones/query/getValue")
    public Mono<String> getValue() {
        return Mono.just("OK");
    }

    private Mono<ResponseJobQueryDto> buildDto(JobResponse response) {
        return Mono.just(ResponseJobQueryDto.builder()
                        .jobCanonical(response.getJobExecutionCanonical())
                .build());
    }

    private ResponseEntity<Mono<ResponseJobQueryDto>> handleErrors(Exception error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Mono.just(ResponseJobQueryDto.builder()
                                .error(error.toString())
                                .build()));

    }

}
