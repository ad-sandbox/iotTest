package de.appdynamics.ace.test.iot.rmi;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.Command;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ServerCmd extends AbstractCommand {

    Logger logger = LoggerFactory.getLogger(ServerCmd.class);


    @Override
    protected List<Option> getCLIOptionsImpl() {
        return CLIOptions.generateBasicOptions();
    }

    @Override
    protected int executeImpl(OptionWrapper options) throws CommandException {

        logger.debug("HSG");
        logger.warn("JJ");
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
