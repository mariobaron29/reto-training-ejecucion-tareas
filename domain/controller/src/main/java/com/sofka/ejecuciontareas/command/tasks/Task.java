package com.sofka.ejecuciontareas.command.tasks;

import com.sofka.ejecuciontareas.domain.entity.event.JobEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Task implements Job {
    private JobEvent event;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        URL url = null;
        HttpURLConnection con = null;

        try {
            url = new URL(getEvent().getUrl());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            System.out.println("Response code: " + con.getResponseCode());
            System.out.println("Response message: " + con.getResponseMessage());
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JobEvent getEvent() {
        return event;
    }

    public void setEvent(JobEvent event){
        this.event = event;
    }

}
