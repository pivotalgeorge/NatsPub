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

    @RequestMapping(method = RequestMethod.GET, value = "/publish", produces = "application/json")
    public ResponseEntity<String> start(@RequestParam(value = "subject", defaultValue = "HelloWorld") String subject,
                                        @RequestParam(value = "message", defaultValue = "Hello World Message") String message,
                                        @RequestParam(value = "numMessages", defaultValue = "100") long numMessages,
                                        @RequestParam(value = "sleepTime", defaultValue = "100") long sleepTime){
       boolean toSleep = true;

        if(sleepTime == 0){
            toSleep = false;
        }

        if(numMessages == -1){
            while(true){
                natsPub.publish(subject,message);
                if(toSleep){
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            for(int i = 0; i < numMessages; i++){
                natsPub.publish(subject,message);
                if(toSleep){
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);

    }
}
