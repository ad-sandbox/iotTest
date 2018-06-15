package de.appdynamics.ace.test.iot.rmi;

import com.appdynamics.ace.util.cli.CLIUtil;
import com.appdynamics.ace.util.cli.api.api.CommandlineExecution;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Main {

    public static void main(String[] args) {

        ConsoleAppender console = new ConsoleAppender(); //create appender
        //configure the appender
        String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.TRACE);
        console.activateOptions();
        //add appender to any Logger (here is root)
        Logger.getRootLogger().addAppender(console);

        CommandlineExecution cle = new CommandlineExecution("RMI-Tool");

        cle.addCommand(new ServerCmd());
        cle.addCommand(new ClientCmd());


        System.exit(cle.execute(args));
    }
}
