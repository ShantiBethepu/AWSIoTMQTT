package com.ociweb.aws;

import com.ociweb.gl.api.*;
import com.ociweb.pronghorn.network.TLSCertificates;

public class GreenlightningAWSMQTT implements GreenApp
{
    MQTTBridge mqttBridge;
    String CliendId="<your client ID>"; //clientID can be your AWS thing name

    @Override
    public void declareConfiguration(Builder builder) {

        System.out.println("-------------- begin Declare configuration--------------");
        String host="<your API Endpoint>";
        int port=8883;

        mqttBridge=builder.useMQTT(host,port,CliendId)
                .cleanSession(true)
                .useTLS(new TLSCertificates() {
                    @Override
                    public String keyStoreResourceName() {
                        return "<your keystore>"; //keystore has the certificate and the private key from AWS
                    }

                    @Override
                    public String trustStroreResourceName() {
                        return "<your truststore>"; //truststore has RootCA from AWS
                    }

                    @Override
                    public String keyStorePassword() {
                        return "<your keystore password>";
                    }

                    @Override
                    public String keyPassword() {
                        return "<your truststore password>";
                    }

                    @Override
                    public boolean trustAllCerts() {
                        return false;
                    }
                });
        System.out.println("-------------- end Declare configuration--------------");

    }

    @Override
    public void declareBehavior(GreenRuntime runtime) {

        System.out.println("-------------- begin Declare behavior--------------");
        MQTTQoS QoS=MQTTQoS.atLeastOnce;
        String topic="<your topic>";
        //publishes the topic
        Publish publish=new Publish(runtime,topic);
        runtime.registerListener(publish);
        runtime.bridgeTransmission(topic,mqttBridge);
        //subscribes the topic
        Subscribe subscribe=new Subscribe(runtime,topic);
        runtime.registerListener(subscribe).addSubscription(topic,subscribe::recvmesg);
        runtime.bridgeTransmission(topic,mqttBridge);
        runtime.bridgeSubscription(topic,mqttBridge).setQoS(QoS);
        System.out.println("-------------- end Declare behavior--------------");
    }
}
