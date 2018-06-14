package de.appdynamics.ace.test.iot.rmi.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.Random;

public class RMITestServer implements IRMITestServer{


    private final Random _rnd;
    Logger logger = LoggerFactory.getLogger(RMITestServer.class);

    private String _prefix;

    public RMITestServer(String prefix) {
        _prefix = prefix;
        _rnd = new Random();

    }

    @Override
    public RMIMessageObject sayHello(String s) throws RemoteException{
        return sayHelloImpl(s);
    }

    private RMIMessageObject sayHelloImpl(String s) {
        logger.debug("Message Received "+s);
        // Add some random Delay
        try {
            Thread.sleep(200+_rnd.nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new RMIMessageObject(_prefix+s);
    }
}
