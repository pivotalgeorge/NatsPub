package com.jgainey.natspub.demo;

import io.nats.client.AsyncSubscription;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;

import java.io.IOException;

public class NatsPub {

    Connection natsConnection;
    AsyncSubscription subscription;

    public NatsPub() {
        try {
            natsConnection = initConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection initConnection() throws IOException {
        Options options = new Options.Builder()
                .errorCb(ex -> Utils.logError("Connection Exception: " + ex))
                .disconnectedCb(event -> Utils.logError("Channel disconnected: {}" + event.getConnection()))
                .reconnectedCb(event -> Utils.logError("Reconnected to server: {}" + event.getConnection()))
                .build();

        return Nats.connect(System.getenv("NATSAPI"), options);    }

    public void publish(String subject, String stringIn){
        try {
            natsConnection.publish(subject, stringIn.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
