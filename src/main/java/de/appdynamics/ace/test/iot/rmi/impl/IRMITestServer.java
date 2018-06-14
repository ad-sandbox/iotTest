package de.appdynamics.ace.test.iot.rmi.impl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface   IRMITestServer  extends Remote {

    RMIMessageObject sayHello(String obj) throws RemoteException;
}
