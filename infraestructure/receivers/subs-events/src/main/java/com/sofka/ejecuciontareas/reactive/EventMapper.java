package com.sofka.ejecuciontareas.reactive;


import com.sofka.ejecuciontareas.common.ObjectMapperDomain;
import com.sofka.ejecuciontareas.common.event.JobCreatedEvent;
import com.sofka.ejecuciontareas.common.event.notification.CanonicalNotification;
import com.sofka.ejecuciontareas.domain.Job;
import com.sofka.ejecuciontareas.domain.canonical.JobCanonical;
import com.sofka.ejecuciontareas.reactive.dto.*;
import com.sofka.ejecuciontareas.reactive.dto.message.EventMessage;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.nonNull;

@SuppressWarnings("unchecked")
@Component
@RequiredArgsConstructor
public class EventMapper implements ObjectMapperDomain {

    private final ObjectMapper objectMapper;


    public JobCreatedEvent buildCanonicalIn(EventMessage message) {
        if (nonNull(message.getMessageData())) {
            return JobCreatedEvent.builder()
                    .canonical(CanonicalNotification.builder()
                            .data(JobCanonical.builder()
                                    .id(message.getMessageData().getId())
                                    .url(message.getMessageData().getUrl())
                                    .timeZone(message.getMessageData().getTimeZone())
                                    .email(message.getMessageData().getEmail())
                                    .cronRegExp(message.getMessageData().getCronRegExp())
                                    .status(message.getMessageData().getStatus())
                                    .build())
                            .build())
                    .build();
        } else {
            return null;
        }
    }
/*
    private Job buildJob(JobDTO jobDTO) {
        return nonNull(jobDTO) ? objectMapper.mapBuilder(jobDTO, Job.JobBuilder.class)
                .destination(buildDestination(jobDTO.getDestination()))
                .jobItemsList(buildJobItems(jobDTO.getJobItems()))
                .origin(buildOrigin(jobDTO.getOrigin()))
                .originalPrices(buildOriginalPrices(jobDTO.getOriginalPrices()))
                .recipient(buildRecipient(jobDTO.getRecipient()))
                .slot(buildSlot(jobDTO.getSlot()))
                .tasksList(buildTasks(jobDTO.getTasks()))
                .build() : Job.builder().build();
    }

    private List<Tasks> buildTasks(List<TasksDTO> tasksDTOS) {
        return nonNull(tasksDTOS) ?
                mapear(tasksDTOS, tasksDTO -> objectMapper.mapBuilder(tasksDTO, Tasks.TasksBuilder.class)
                        .resource(buildResource(tasksDTO.getResource()))
                        .stepList(buildSteps(tasksDTO.getSteps()))
                        .build()) : null;


    }

    private List<Step> buildSteps(List<StepDTO> stepDTOS) {
        return nonNull(stepDTOS) ? mapear(stepDTOS, stepDTO -> objectMapper.mapBuilder(stepDTO, Step.StepBuilder.class)
                .build()) : null;
    }

    private Resource buildResource(ResourceDTO resourceDTO) {
        return nonNull(resourceDTO) ? objectMapper.mapBuilder(resourceDTO, Resource.ResourceBuilder.class)
                .build() : Resource.builder().build();
    }

    private Slot buildSlot(SlotDTO slotDTO) {
        return nonNull(slotDTO) ? objectMapper.mapBuilder(slotDTO, Slot.SlotBuilder.class)
                .build() : Slot.builder().build();
    }

    private Recipient buildRecipient(RecipientDTO recipientDTO) {
        return nonNull(recipientDTO) ? objectMapper.mapBuilder(recipientDTO, Recipient.RecipientBuilder.class)
                .identification(buildIdentification(recipientDTO.getIdentification()))
                .build() : Recipient.builder().build();
    }

    private Identification buildIdentification(IdentificationDTO identificationDTO) {
        return nonNull(identificationDTO) ?
                objectMapper.mapBuilder(identificationDTO, Identification.IdentificationBuilder.class)
                        .build() : Identification.builder().build();
    }

    private OriginalPrices buildOriginalPrices(OriginalPricesDTO originalPricesDTO) {
        return nonNull(originalPricesDTO) ? objectMapper.mapBuilder(originalPricesDTO, OriginalPrices.OriginalPricesBuilder.class)
                .build() : OriginalPrices.builder().build();
    }

    private Origin buildOrigin(OriginDTO originDTO) {
        return nonNull(originDTO) ? objectMapper.mapBuilder(originDTO, Origin.OriginBuilder.class)
                .build() : Origin.builder().build();
    }

    private List<JobItems> buildJobItems(List<JobItemsDTO> jobItemsDTOS) {
        return nonNull(jobItemsDTOS) ? mapear(jobItemsDTOS, jobItemsDTO -> objectMapper.mapBuilder(jobItemsDTO, JobItems.JobItemsBuilder.class)
                .attributes(buildAttributes(jobItemsDTO.getAttributes()))
                .build()) : null;
    }

    private Attribute buildAttributes(AttributeDTO attributeDTO) {
        return nonNull(attributeDTO) ? objectMapper.mapBuilder(attributeDTO, Attribute.AttributeBuilder.class)
                .build() : Attribute.builder().build();
    }

    private Destination buildDestination(DestinationDTO destinationDTO) {
        return nonNull(destinationDTO) ? objectMapper.mapBuilder(destinationDTO, Destination.DestinationBuilder.class)
                .build() : Destination.builder().build();
    }

*/
}
