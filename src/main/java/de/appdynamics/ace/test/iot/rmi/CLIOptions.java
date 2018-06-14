package de.appdynamics.ace.test.iot.rmi;

import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.List;

public class CLIOptions {

    public static final String ARG_PORT = "port";

    public static List<Option> generateBasicOptions() {
        List<Option> options = new ArrayList<>();


        Option o;
        options.add(o = new Option (ARG_PORT,true,"portnumber"));
        o.setRequired(true);

        return options;
    }
}
