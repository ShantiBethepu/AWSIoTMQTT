package com.ociweb.aws;

import com.ociweb.gl.api.*;

public class Publish implements StartupListener, PubSubMethodListener {
    GreenCommandChannel cmd;
    String publishtopic;
    Writable message = writer -> writer.writeUTF("{\n" +
            "  \"message\": \"Hello from GreenLightning \"\n" +
            "} ");

    public Publish(GreenRuntime runtime, String topic)
    {
        System.out.println("\nOk, this is in mqttbehavior");
        cmd = runtime.newCommandChannel(DYNAMIC_MESSAGING);
        this.publishtopic = topic;
    }
    @Override
    public void startup() {
        try {
            System.out.println("\nPublishing this topic : " + publishtopic);
            cmd.publishTopic(publishtopic, message);
            System.out.println("\nSuccessfully published! Check AWS now ");
        } catch (Exception e) {
            System.out.println("Error in startup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
