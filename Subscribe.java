package com.ociweb.aws;
import com.ociweb.gl.api.GreenCommandChannel;
import com.ociweb.gl.api.GreenRuntime;
import com.ociweb.gl.api.PubSubMethodListener;
import com.ociweb.gl.api.StartupListener;
import com.ociweb.pronghorn.pipe.ChannelReader;
public class Subscribe implements StartupListener,PubSubMethodListener 
{
    GreenCommandChannel cmd;
    String subcribetopic;
    public Subscribe(GreenRuntime runtime, String topic)
    {
        cmd = runtime.newCommandChannel(DYNAMIC_MESSAGING);
        this.subcribetopic = topic;
    }
    @Override
    public void startup() {
        System.out.println("\nSubscribing to the topic "+subcribetopic);
        cmd.subscribe(subcribetopic);
        System.out.println("\nSuccessfully subcribed to the topic: "+subcribetopic+"\n"+"go to AWS and publish the topic now");
    }
    public boolean recvmesg(CharSequence topic, ChannelReader reader) {
        String receivedmessage = reader.readUTFOfLength(reader.available());
        System.out.println("Message received: " + receivedmessage);
        return true;
    }
}











