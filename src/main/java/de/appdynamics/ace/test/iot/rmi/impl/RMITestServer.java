package de.appdynamics.ace.test.iot.rmi.impl;

import de.appdynamics.ace.test.iot.rmi.ServerCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class RMITestServer implements IRMITestServer{


    Logger logger = LoggerFactory.getLogger(RMITestServer.class);

    private String _prefix;

    public RMITestServer(String prefix) {
        _prefix = prefix;
    }

    @Override
    public String sayHello(String s) throws RemoteException{
        logger.debug("Message Received "+s);
        return _prefix+s;
    }
}
