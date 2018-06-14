package de.appdynamics.ace.test.iot.rmi;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.Command;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import de.appdynamics.ace.test.iot.rmi.impl.IRMITestServer;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;

import static de.appdynamics.ace.test.iot.rmi.CLIOptions.ARG_PORT;

public class ClientCmd extends AbstractCommand {

    Logger logger = LoggerFactory.getLogger(ClientCmd.class);

    @Override
    protected List<Option> getCLIOptionsImpl() {
        return CLIOptions.generateBasicOptions();
    }

    @Override
    protected int executeImpl(OptionWrapper options) throws CommandException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", Integer.parseInt(options.getOptionValue(ARG_PORT)));
            IRMITestServer stub = (IRMITestServer) reg.lookup("TST");
            String msg = new Date().toString();
            logger.debug("   Send Data:"+msg);
            logger.debug("   Received:"+stub.sayHello(msg));
        } catch (RemoteException e) {
            logger.error("Couldn Find Registy at localhost:"+options.getOptionValue(ARG_PORT),e);
        } catch (NotBoundException e) {
            logger.error("Error locating Stubb",e);
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Client";
    }

    @Override
    public String getDescription() {
        return "Start Client PRocess";
    }
}
