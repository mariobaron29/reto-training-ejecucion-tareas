package com.sofka.ejecuciontareas;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Task implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL("http://example.com");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            System.out.println("Response code: " + con.getResponseCode());
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
