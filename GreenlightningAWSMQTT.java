package com.ociweb.aws;

import com.ociweb.gl.api.*;
import com.ociweb.pronghorn.network.TLSCertificates;

public class GreenlightningAWSMQTT implements GreenApp
{
    MQTTBridge mqttBridge;
    String CliendId="GreenLightningThing";

    @Override
    public void declareConfiguration(Builder builder) {

        System.out.println("-------------- begin Declare configuration--------------");
        String host="a235s0ler27298.iot.us-east-1.amazonaws.com";
        int port=8883;

        mqttBridge=builder.useMQTT(host,port,CliendId)
                .cleanSession(true)
                .useTLS(new TLSCertificates() {
                    @Override
                    public String keyStoreResourceName() {
                        return "/certificate/CertandKey.p12";
                    }

                    @Override
                    public String trustStroreResourceName() {
                        return "/certificate/TrustStore.jks";
                    }

                    @Override
                    public String keyStorePassword() {
                        return "nopassword";
                    }

                    @Override
                    public String keyPassword() {
                        return "nopassword";
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
        String topic="$aws/things/MqttThing/shadow/get";
/*

        Publish publish=new Publish(runtime,topic);
        runtime.registerListener(publish);
        runtime.bridgeTransmission(topic,mqttBridge);
*/

        Subscribe subscribe=new Subscribe(runtime,topic);
        runtime.registerListener(subscribe).addSubscription(topic,subscribe::recvmesg);
        runtime.bridgeTransmission(topic,mqttBridge);
        runtime.bridgeSubscription(topic,mqttBridge).setQoS(QoS);
        System.out.println("-------------- end Declare behavior--------------");

    }

}




/*String topic="$aws/things/MqttThing/shadow/get";
        MQTTBehavior mqttBehavior=new MQTTBehavior(runtime,topic);
       // runtime.registerListener(mqttBehavior);
        runtime.bridgeTransmission(topic,mqttBridge);
        runtime.bridgeSubscription(topic,mqttBridge);
        runtime.registerListener(mqttBehavior).addSubscription(topic,mqttBehavior::recvmesg);*/
// runtime.addPubSubListener(mqttBehavior,topic);
// runtime.registerListener(mqttBehavior);
// runtime.addPubSubListener(mqttBehavior);
//  runtime.registerListener(mqttBehavior)

