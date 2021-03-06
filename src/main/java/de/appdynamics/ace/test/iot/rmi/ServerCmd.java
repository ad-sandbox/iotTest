package de.appdynamics.ace.test.iot.rmi;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.Command;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import de.appdynamics.ace.test.iot.rmi.impl.IRMITestServer;
import de.appdynamics.ace.test.iot.rmi.impl.RMITestServer;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static de.appdynamics.ace.test.iot.rmi.CLIOptions.ARG_PORT;

public class ServerCmd extends AbstractCommand {

    Logger logger = LoggerFactory.getLogger(ServerCmd.class);


    @Override
    protected List<Option> getCLIOptionsImpl() {
        return CLIOptions.generateBasicOptions();
    }

    @Override
    protected int executeImpl(OptionWrapper options) throws CommandException {

        try {
            Registry reg = LocateRegistry.createRegistry(Integer.parseInt(options.getOptionValue(ARG_PORT)));
            RMITestServer srv = new RMITestServer("Blubb:");

            IRMITestServer stub = (IRMITestServer) UnicastRemoteObject.exportObject(srv, 0);
            reg.bind("TST",stub);

            logger.debug("Server Ready");
            for (;;) {
                logger.debug("Waiting");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {

                }
            }
        } catch (RemoteException e) {
            logger.error("Error while creating Server",e);
        } catch (AlreadyBoundException e) {
            logger.error("Couldn't Bind implementation",e);
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public String getDescription() {
        return "Starts the Server Stub";
    }
}
