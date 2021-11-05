package com.sofka.ejecuciontareas.command.tasks;

import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class CustomJobFactory extends SimpleJobFactory {
    private JobEvent event;

    public CustomJobFactory(JobEvent event){
        this.event = event;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
        Task job = (Task) super.newJob(bundle, Scheduler);
        job.setEvent(event);
        return job;
    }
}