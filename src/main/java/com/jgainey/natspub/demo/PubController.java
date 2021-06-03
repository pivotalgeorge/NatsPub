package com.jgainey.natspub.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PubController {


    NatsPub natsPub = new NatsPub();

    @RequestMapping(method = RequestMethod.GET, value = "/connect", produces = "application/json")
    public ResponseEntity<String> connect(){
        natsPub.connect();
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/disconnect", produces = "application/json")
    public ResponseEntity<String> disconnect(){
        natsPub.disconnect();
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/publish", produces = "application/json")
    public ResponseEntity<String> start(@RequestParam(value = "subject", defaultValue = "HelloWorld") String subject,
                                        @RequestParam(value = "message", defaultValue = "Hello World Message") String origMessage,
                                        @RequestParam(value = "counter", defaultValue = "false") boolean appendCounter,
                                        @RequestParam(value = "log", defaultValue = "false") boolean logMessages,
                                        @RequestParam(value = "numMessages", defaultValue = "100") long numMessages,
                                        @RequestParam(value = "sleepTime", defaultValue = "100") long sleepTime){
        String messageToPublish = origMessage;

        if(numMessages == -1){
            int i = 0;
            while(true){
                if(appendCounter) {
                    messageToPublish = origMessage + " counter: " + i;
                    i++;
                }
                natsPub.publish(subject, messageToPublish);
                if(logMessages) {
                    Utils.logInfo("published message: " + messageToPublish);
                }
                trySleep(sleepTime);
            }
        } else {
            for(int i = 0; i < numMessages; i++){
                if(appendCounter) {
                    messageToPublish = origMessage + " counter: " + i;
                }
                natsPub.publish(subject, messageToPublish);
                if(logMessages) {
                    Utils.logInfo("published message: " + messageToPublish);
                }
                trySleep(sleepTime);
            }
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);

    }
    // private String messageWithCounter(boolean count, String message) {
    //     if (toTimestamp) {
    //       return "
    //     )
    //     return message;
    // }

    private void trySleep(long sleepTime) {
        boolean toSleep = true;
        if(sleepTime == 0){
            toSleep = false;
        }
        if(toSleep){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
